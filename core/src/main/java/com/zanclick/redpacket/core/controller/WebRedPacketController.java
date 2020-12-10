package com.zanclick.redpacket.core.controller;

import com.alibaba.fastjson.JSONObject;
import com.zanclick.redpacket.common.entity.ExcelDto;
import com.zanclick.redpacket.common.entity.Response;
import com.zanclick.redpacket.common.jms.SendMessage;
import com.zanclick.redpacket.common.queue.contents.RedPacketContents;
import com.zanclick.redpacket.common.ratelimit.anonation.RateLimit;
import com.zanclick.redpacket.common.utils.DataUtils;
import com.zanclick.redpacket.common.utils.DateUtils;
import com.zanclick.redpacket.common.utils.LoginContext;
import com.zanclick.redpacket.common.utils.PoiUtil;
import com.zanclick.redpacket.core.entity.FileExport;
import com.zanclick.redpacket.core.entity.RedPacket;
import com.zanclick.redpacket.core.query.FileExportQuery;
import com.zanclick.redpacket.core.query.RedPacketQuery;
import com.zanclick.redpacket.core.service.FileExportService;
import com.zanclick.redpacket.core.service.RedPacketService;
import com.zanclick.redpacket.core.vo.RedPacketVo;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @Author: huze
 * @Date: 2020/12/8 10:22
 */

@Slf4j
@RestController
@RequestMapping(value = "/api/web/redPacket")
public class WebRedPacketController {

    @Value("${serverPath}")
    private String serverPath;
    @Autowired
    private RedPacketService redPacketService;

    @Autowired
    private FileExportService fileExportService;

    /**
     * 红包列表
     *
     * @param query
     * @return
     */
    @RequestMapping("list")
    public Response<Map<String, Object>> list(RedPacketQuery query) {
        if (DataUtils.isEmpty(query.getPage())) {
            query.setPage(0);
        }
        if (DataUtils.isEmpty(query.getLimit())) {
            query.setLimit(10);
        }
        try {
            Pageable pageable = PageRequest.of(query.getPage(), query.getLimit());
            Page<RedPacket> page = redPacketService.queryPage(query, pageable);
            List<RedPacketVo> voList = new ArrayList<>();
            for (RedPacket redPacket : page.getContent()) {
                voList.add(getListVo(redPacket));
            }
            Page<RedPacketVo> voPage = new PageImpl<RedPacketVo>(voList, pageable, page.getTotalElements());
            Map<String, Object> map = new HashMap<>(2);
            map.put("data", voPage);
            map.put("sumRebateAmount", redPacketService.selectSunAmount(query));
            return Response.ok(map);
        } catch (Exception e) {
            log.error("获取交易列表异常:{}", e);
            return Response.fail("查询失败");
        }
    }
    private RedPacketVo getListVo(RedPacket redPacket) {
        RedPacketVo vo = new RedPacketVo();
        vo.setBrwOrdNo(redPacket.getBrwOrdNo());
        vo.setTypeDesc(redPacket.getTypeDesc());
        vo.setAmount(redPacket.getAmount());
        vo.setAppId(redPacket.getAppId());
        vo.setTradeNo(redPacket.getTradeNo());
        vo.setOutTradeNo(redPacket.getOutTradeNo());
        vo.setTitle(redPacket.getTitle());
        vo.setState(redPacket.getState());
        vo.setStateDesc(redPacket.getStateDesc());
        vo.setType(redPacket.getType());
        vo.setMerchantNo(redPacket.getMerchantNo());
        vo.setCashierPhoneNo(redPacket.getCashierPhoneNo());
        vo.setSellerNo(redPacket.getSellerNo());
        vo.setCustomPhone(redPacket.getCustomPhone());
        vo.setOrderTime(DateUtils.formatDate(redPacket.getOrderTime(), DateUtils.PATTERN_YYYY_MM_DD_HH_MM_SS));
        vo.setDelayDays(redPacket.getDelayDays());
        vo.setIsNextMonthSettle(redPacket.getIsNextMonthSettle());
        vo.setCreateTime(DateUtils.formatDate(redPacket.getCreateTime(), DateUtils.PATTERN_YYYY_MM_DD_HH_MM_SS));
        vo.setCollectAccountNumber(redPacket.getCollectAccountNumber());
        vo.setAccountNo(redPacket.getAccountNo());
        vo.setProvinceName(redPacket.getProvinceName());
        vo.setCashierNo(redPacket.getCashierNo());
        vo.setTradingTime(DateUtils.formatDate(redPacket.getTradingTime(), DateUtils.PATTERN_YYYY_MM_DD_HH_MM_SS));
        return vo;
    }

    @ApiOperation(value = "导出商户信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "authorization", value = "加密参数", required = true, dataType = "String", paramType = "header"),
    })
    @RequestMapping(value = "batchExportMerchant", method = RequestMethod.POST)
    @ResponseBody
    @RateLimit(permitsPerSecond = 0.2, ipLimit = true, description = "限制导出频率")
    public Response<String> batchExportMerchant(RedPacketQuery query) throws ParseException {
        LoginContext.RequestUser user = LoginContext.getCurrentUser();
        //线程池方式
        ArrayBlockingQueue abq = new ArrayBlockingQueue(10);
        ThreadPoolExecutor executor = new ThreadPoolExecutor(5, 10, 300, TimeUnit.SECONDS, abq);
        //先查看是否有导出中的任务
        FileExportQuery fileExportQuery = new FileExportQuery();
        fileExportQuery.setUser(user.getUsername());
        List<FileExport> fileExports = fileExportService.queryList(fileExportQuery);
        for (FileExport expor : fileExports) {
            if (expor.getState() == 2) {
                return Response.fail("有任务正在导出中请稍后再试！");
            }
        }
        String name = "红包列表";
        FileExport fileExport = new FileExport();
        Long time = System.currentTimeMillis();
        String timeStamp = time.toString();
        String filename = serverPath + name + "-" + timeStamp + ".xlsx";
        fileExport.setFileName(name + "-" + timeStamp);
        fileExport.setExprotTime(new Date());
        fileExport.setState(2);
        fileExport.setUser(user.getUsername());
        fileExportService.insert(fileExport);
        executor.execute(new Thread(() -> {
            try {
                List<RedPacket> redPacketList = redPacketService.queryList(query);
                List<RedPacketVo> redPacketVoList = new ArrayList<>();
                int index = 1;
                for (RedPacket operateLog : redPacketList) {
                    RedPacketVo listVo = getListVo(operateLog);
                    listVo.setIndex(index);
                    redPacketVoList.add(listVo);
                    index++;
                }
                ExcelDto dto = new ExcelDto();
                dto.setHeaders(RedPacketVo.headers);
                dto.setKeys(RedPacketVo.keys);
                dto.setObjectList(parser(redPacketVoList));
                downloadExcel(dto, filename);
                fileExport.setState(3);
                fileExportService.updateById(fileExport);
            } catch (Exception e) {
                log.error("生成文件失败", e);
                fileExport.setState(1);
                fileExportService.updateById(fileExport);
            }
            executor.shutdown();
        }));
        return Response.ok("生成成功！请到文件管理中查看下载!");
    }

    private List<JSONObject> parser(List<RedPacketVo> payOrderOperateLogList) {
        return JSONObject.parseArray(JSONObject.toJSONString(payOrderOperateLogList), JSONObject.class);
    }

    public void downloadExcel(ExcelDto dto, String filename) throws Exception {
        PoiUtil.batchExport(dto.getHeaders(), dto.getKeys(), dto.getObjectList(), filename);
    }

    @ApiOperation(value = "酬金列表打款")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "authorization", value = "加密参数", required = true, dataType = "String", paramType = "header"),
    })
    @RequestMapping(value = "settle", method = RequestMethod.POST)
    @ResponseBody
    public Response<String> settle(String brwOrdNo) {
        if (DataUtils.isEmpty(brwOrdNo)) {
            log.error("酬金打款失败,数据不存在:{}", brwOrdNo);
            return Response.fail("酬金打款失败,数据不存在");
        }
        RedPacket redPacket = redPacketService.selectBybrwOrdNo(brwOrdNo);
        if (DataUtils.isEmpty(redPacket)) {
            log.error("酬金打款失败,数据不存在:{}", brwOrdNo);
            return Response.fail("酬金打款失败,数据不存在");
        }
        if (redPacket.isSuccess()) {
            log.error("酬金打款失败,已打款成功:{}", brwOrdNo);
            return Response.fail("酬金打款失败,已打款成功");
        }
        if (redPacket.isWaiting()) {
            log.error("酬金打款失败,正在打款中:{}", brwOrdNo);
            return Response.fail("酬金打款失败,正在打款中");
        }
        if (redPacket.isRefund()) {
            log.error("酬金打款失败,用户已退单:{}", brwOrdNo);
            return Response.fail("酬金打款失败,用户已退单");
        }
        try {
            //TODO 同步或者异步
            //rebateRecordService.settle(rebate);
            sendSettleMsg(brwOrdNo);
        } catch (Exception e) {
            log.error("酬金打款异常:{},{}", brwOrdNo, e);
            return Response.fail("酬金打款异常");
        }
        return Response.ok("酬金打款中");
    }
    /**
     * 红包打款
     *
     * @param brwOrdNo
     */
    private void sendSettleMsg(String brwOrdNo) {
        JSONObject object = new JSONObject();
        object.put("brwOrdNo", brwOrdNo);
        SendMessage.sendMessage(RedPacketContents.REDPACKET_SETTLE_MESSAGE, object.toString());
    }

}
