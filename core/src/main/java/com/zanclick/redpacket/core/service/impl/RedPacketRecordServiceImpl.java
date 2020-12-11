package com.zanclick.redpacket.core.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zanclick.redpacket.common.base.dao.mybatis.BaseMapper;
import com.zanclick.redpacket.common.base.service.impl.BaseMybatisServiceImpl;
import com.zanclick.redpacket.common.utils.DataUtils;
import com.zanclick.redpacket.common.utils.DateUtil;
import com.zanclick.redpacket.common.utils.DateUtils;
import com.zanclick.redpacket.common.utils.StringUtils;
import com.zanclick.redpacket.core.dto.DataList;
import com.zanclick.redpacket.core.entity.RedPacket;
import com.zanclick.redpacket.core.entity.RedPacketRecord;
import com.zanclick.redpacket.core.lx.response.HbOrderResponse;
import com.zanclick.redpacket.core.mapper.RedPacketRecordMapper;
import com.zanclick.redpacket.core.service.RedPacketRecordService;
import com.zanclick.redpacket.core.service.RedPacketService;
import com.zanclick.redpacket.core.util.HttpClientUtil;
import com.zanclick.redpacket.core.util.ThreeDesUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @author admin
 * @date 2020-12-10 10:25:42
 **/
@Service
@Slf4j
public class RedPacketRecordServiceImpl extends BaseMybatisServiceImpl<RedPacketRecord,Long> implements RedPacketRecordService {

    @Autowired
    private RedPacketRecordMapper redPacketRecordMapper;

    @Autowired
    private RedPacketService redPacketService;


    @Value("${lx.appKey}")
    private String appKey;
    @Value("${lx.mchCode}")
    private String mchCode;
    @Value("${lx.serect}")
    private String serect;
    @Value("${lx.host}")
    private String host;
    @Value("${lx.method.hbPay}")
    private String hbPay;
    @Override
    protected BaseMapper<RedPacketRecord, Long> getBaseMapper() {
        return redPacketRecordMapper;
    }

    @Override
    public RedPacketRecord findByPacketNo(String packetNo) {
        return redPacketRecordMapper.findByPacketNo(packetNo);
    }
    @Override
    public RedPacketRecord findByOutTradeNo(String outTradeNo) {
        return redPacketRecordMapper.findByOutTradeNo(outTradeNo);
    }
    @Override
    public void allSettle(List<RedPacket> list) {
        try {
            //线程数自定义
            int threadNum = 5;
            //给每个线程分发处理条数(总条数/线程数);
            int threadSize = list.size() / threadNum;
            //创建线程池
            ExecutorService eService = Executors.newFixedThreadPool(threadNum);
            List<Callable<String>> cList = new ArrayList<>();
            Callable<String> task = null;
            List<RedPacket> sList = null;
            for (int i = 0; i < threadNum; i++) {
                if (i == threadNum - 1) {
                    sList = list.subList(i * threadSize, list.size());
                } else {
                    sList = list.subList(i * threadSize, (i + 1) * threadSize);
                }
                final List<RedPacket> nowList = sList;
                task = new Callable<String>() {
                    @Override
                    public String call() {
                        Integer successCount = 0;
                        Integer failCount = 0;
                        BigDecimal money = new BigDecimal("0.00");
                        if (DataUtils.isNotEmpty(nowList)) {
                            for (RedPacket redPacket : nowList) {
                                // 调用业务方法
                                log.error("开始进行酬金打款,brwOrdNo:{},amount:{}", redPacket.getBrwOrdNo(), redPacket.getAmount());
                                Boolean result = settle(redPacket);
                                if (true) {
                                    successCount++;
                                    money = money.add(new BigDecimal(redPacket.getAmount()));
                                } else {
                                    failCount++;
                                }
                                try {
                                    Thread.sleep(2000);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                        return new StringBuilder().append(successCount).append("|").append(failCount).append("|").append(money).toString();
                    }
                };

                cList.add(task);
            }
            try {
                List<Future<String>> futures = eService.invokeAll(cList);
                Integer successCount = 0;
                Integer failCount = 0;
                BigDecimal money = new BigDecimal("0.00");
                for (Future<String> future : futures) {
                    String result = future.get();
                    String[] split = result.split("\\|");
                    if (split[0]!=null){
                        successCount= successCount+Integer.valueOf(split[0]);
                    }
                    if (split[1]!=null) {
                        failCount = failCount + Integer.valueOf(split[1]);
                    }
                    if (split[2]!=null) {
                        money = money.add(new BigDecimal(split[2]));
                    }
                }
                log.error(DateUtil.getNowDate() + "批量酬金打款结束，共计打款{}笔，金额{}元，失败{}笔", successCount, money.toString(), failCount);
            } catch (InterruptedException e) {
                log.error("全部打款异常:{}", e);
            }
            eService.shutdown();
        } catch (Exception e) {
            log.error("全部打款异常:{}", e);
        }
    }


    @Override
    public Boolean settle(RedPacket redPacket) {
        if (DataUtils.isEmpty(redPacket)) {
            log.error("酬金打款失败,数据不存在:{}", redPacket.getOutTradeNo());
            return false;
        }
        if (redPacket.isSuccess()) {
            log.error("酬金打款失败,已打款成功:{}", redPacket.getOutTradeNo());
            return false;
        }
        if (redPacket.isWaiting()) {
            log.error("酬金打款失败,正在打款中:{}", redPacket.getOutTradeNo());
            return false;
        }
        if (redPacket.isRefund()) {
            log.error("酬金打款失败,用户已退单:{}", redPacket.getOutTradeNo());
            return false;
        }
        //创建打款记录
        RedPacketRecord rebateRecord = this.createRebateRecord(redPacket, RedPacketRecord.State.WAITING.getCode());
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
        String today = df.format(new Date());
        // String today = ThreeDesUtils.getSecondTimestamp(new Date());
        String orderId = rebateRecord.getOutTradeNo();
        // 接口调用
        try {
            //请求和包下单接口
            String dataList = getDataLists(redPacket.getTradeNo(), redPacket.getAmount(), redPacket.getTitle());
            String data = ThreeDesUtils.getHBData(orderId, mchCode, "测试", redPacket.getSellerNo(), redPacket.getSellerNo(), redPacket.getAmount(),
                    redPacket.getBrwOrdNo(), DateUtils.formatDate(redPacket.getCreateTime(),DateUtils.PATTERN_YYYY_MM_DD_HH_MM_SS), redPacket.getProvinceCode(), serect, dataList);
            log.info("data为：{}", data);
            String sign = ThreeDesUtils.getSign(appKey, data, orderId, today);
            Map<String, String> parmMap = new HashMap<>(7);
            parmMap.put("data", data);
            parmMap.put("sign", sign);
            parmMap.put("mess", "mess" + orderId);
            parmMap.put("timestamp", today);
            parmMap.put("sign_type", "SHA256");
            parmMap.put("request-id", orderId);
            parmMap.put("dealer-id", mchCode);
            //测试环境
            String requestUrl = host + hbPay;
//            String result = HttpClientUtil.postPayMsg(requestUrl, parmMap);
//            HbOrderResponse response = JSON.parseObject(result, HbOrderResponse.class);
//            log.info("和包支付实时下单请求结果为：{}", JSONObject.toJSONString(result));
            //结果校验
//            if (response.isSuccess()) {
            if (true) {
                //修改酬金表和记录表 打款中
                log.error("打款中,brwOrdNo:{},amount:{}", redPacket.getBrwOrdNo(), redPacket.getAmount());
                redPacket.setState(RedPacket.State.RECEIVED.getCode());
                rebateRecord.setState(RedPacketRecord.State.RECEIVED.getCode());
                redPacketService.updateById(redPacket);
                this.updateById(rebateRecord);
                return true;
            } else {
//                log.error("打款失败:brwOrdNo:{},{}", redPacket.getBrwOrdNo(), JSONObject.toJSONString(result));
                redPacket.setState(RedPacket.State.FAIL.getCode());
//                rebateRecord.setReason(response.getMessage());
                rebateRecord.setState(RedPacketRecord.State.FAIL.getCode());
                redPacketService.updateById(redPacket);
                this.updateById(rebateRecord);
                return false;
            }
        } catch (Exception e) {
            log.error("和包支付实时下单请求结果为:{},{}", redPacket.getBrwOrdNo(), e);
            redPacket.setState(RedPacket.State.FAIL.getCode());
            rebateRecord.setReason("打款请求异常");
            rebateRecord.setState(RedPacketRecord.State.FAIL.getCode());
            redPacketService.updateById(redPacket);
            this.updateById(rebateRecord);
            return false;
        }

    }

    @Override
    public RedPacketRecord createRebateRecord(RedPacket rebate, Integer state) {
        RedPacketRecord redPacketRecord = new RedPacketRecord();
        redPacketRecord.setBrwOrdNo(rebate.getBrwOrdNo());
        redPacketRecord.setCreateTime(rebate.getCreateTime());
        if (!RedPacketRecord.State.WAITING.getCode().equals(state)) {
            redPacketRecord.setOutTradeNo(StringUtils.getTradeNo());
            redPacketRecord.setTradeNo(StringUtils.getTradeNo());
        }
        redPacketRecord.setTradeNo(rebate.getTradeNo());
        redPacketRecord.setOutTradeNo(rebate.getOutTradeNo());
        redPacketRecord.setAmount(rebate.getAmount());
        redPacketRecord.setTitle(rebate.getTitle());
        redPacketRecord.setAppId(rebate.getAppId());
        redPacketRecord.setState(state);
        redPacketRecord.setType(rebate.getType());
        redPacketRecord.setMerchantNo(rebate.getMerchantNo());
        redPacketRecord.setCashierPhoneNo(rebate.getCashierPhoneNo());
        redPacketRecord.setSellerNo(rebate.getSellerNo());
        redPacketRecord.setAccountNo(rebate.getAccountNo());
        redPacketRecord.setProvinceName(rebate.getProvinceName());
        redPacketRecord.setProvinceCode(rebate.getProvinceCode());
        redPacketRecord.setCollectAccountNumber(rebate.getCollectAccountNumber());
        redPacketRecord.setCashierNo(rebate.getCashierNo());
        this.insert(redPacketRecord);
        return redPacketRecord;
    }

    private String getDataLists(String tradeNo, String amount, String productNm) {
        DataList dataList = new DataList();
        dataList.setTradeId(tradeNo);
        dataList.setMoney(amount);
        dataList.setBusinessName(productNm);
        List<DataList> dataLists = new ArrayList<>();
        dataLists.add(dataList);
        String jsonList = JSON.toJSONString(dataLists);
        return jsonList;
    }
}
