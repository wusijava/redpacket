package com.zanclick.redpacket.core.controller;

import com.alibaba.fastjson.JSONObject;
import com.zanclick.redpacket.common.entity.ExcelDto;
import com.zanclick.redpacket.common.entity.Response;
import com.zanclick.redpacket.common.ratelimit.anonation.RateLimit;
import com.zanclick.redpacket.common.utils.DataUtils;
import com.zanclick.redpacket.common.utils.DateUtils;
import com.zanclick.redpacket.common.utils.LoginContext;
import com.zanclick.redpacket.common.utils.PoiUtil;
import com.zanclick.redpacket.core.entity.FileExport;
import com.zanclick.redpacket.core.entity.RedPacket;
import com.zanclick.redpacket.core.entity.RedPacketRecord;
import com.zanclick.redpacket.core.query.FileExportQuery;
import com.zanclick.redpacket.core.query.RedPacketQuery;
import com.zanclick.redpacket.core.query.RedPacketRecordQuery;
import com.zanclick.redpacket.core.service.FileExportService;
import com.zanclick.redpacket.core.service.RedPacketRecordService;
import com.zanclick.redpacket.core.vo.RedPacketRecordVo;
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
 * @Date: 2020/12/8 14:17
 */
@Slf4j
@RestController
@RequestMapping(value = "/api/web/redPacketRecord")
public class WebRedPacketRecordController {
    @Autowired
    private RedPacketRecordService redPacketRecordService;
    @Autowired
    private FileExportService fileExportService;

    @Value("${serverPath}")
    private String serverPath;


    /**
     * 红包列表
     *
     * @param query
     * @return
     */
    @RequestMapping("list")
    public Response<Page<RedPacketRecordVo>> list(RedPacketRecordQuery query) {
        if (DataUtils.isEmpty(query.getPage())) {
            query.setPage(0);
        }
        if (DataUtils.isEmpty(query.getLimit())) {
            query.setLimit(10);
        }
        try {
            Pageable pageable = PageRequest.of(query.getPage(), query.getLimit());
            Page<RedPacketRecord> page = redPacketRecordService.queryPage(query, pageable);
            List<RedPacketRecordVo> voList = new ArrayList<>();
            for (RedPacketRecord redPacketRecord : page.getContent()) {
                voList.add(getListVo(redPacketRecord));
            }
            Page<RedPacketRecordVo> voPage = new PageImpl<RedPacketRecordVo>(voList, pageable, page.getTotalElements());
            return Response.ok(voPage);
        } catch (Exception e) {
            log.error("获取交易列表异常:{}", e);
            return Response.fail("查询失败");
        }
    }

    @ApiOperation(value = "导出商户信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "authorization", value = "加密参数", required = true, dataType = "String", paramType = "header"),
    })
    @RequestMapping(value = "batchExportMerchant", method = RequestMethod.POST)
    @ResponseBody
    @RateLimit(permitsPerSecond = 0.2, ipLimit = true, description = "限制导出频率")
    public Response<String> batchExportMerchant(RedPacketRecordQuery query) throws ParseException {
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
        String name = "红包领取记录列表";
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
                List<RedPacketRecord> redPacketRecordList = redPacketRecordService.queryList(query);
                List<RedPacketRecordVo> redPacketVoList = new ArrayList<>();
                int index = 1;
                for (RedPacketRecord redPacketRecord : redPacketRecordList) {
                    RedPacketRecordVo listVo = getListVo(redPacketRecord);
                    listVo.setIndex(index);
                    redPacketVoList.add(listVo);
                    index++;
                }
                ExcelDto dto = new ExcelDto();
                dto.setHeaders(RedPacketRecordVo.headers);
                dto.setKeys(RedPacketRecordVo.keys);
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

    private List<JSONObject> parser(List<RedPacketRecordVo> payOrderOperateLogList) {
        return JSONObject.parseArray(JSONObject.toJSONString(payOrderOperateLogList), JSONObject.class);
    }

    public void downloadExcel(ExcelDto dto, String filename) throws Exception {
        PoiUtil.batchExport(dto.getHeaders(), dto.getKeys(), dto.getObjectList(), filename);
    }

    private RedPacketRecordVo getListVo(RedPacketRecord redPacketRecord) {
        RedPacketRecordVo vo = new RedPacketRecordVo();
        vo.setCustomPhone(redPacketRecord.getCustomPhone());
        vo.setStateDesc(redPacketRecord.getStateDesc());
        vo.setAmount(redPacketRecord.getAmount());
        vo.setTradeNo(redPacketRecord.getTradeNo());
        vo.setOutTradeNo(redPacketRecord.getOutTradeNo());
        vo.setTitle(redPacketRecord.getTitle());
        vo.setAppId(redPacketRecord.getAppId());
        vo.setState(redPacketRecord.getState());
        vo.setType(redPacketRecord.getType());
        vo.setMerchantNo(redPacketRecord.getMerchantNo());
        vo.setSellerNo(redPacketRecord.getSellerNo());
        vo.setCreateTime(DateUtils.formatDate(redPacketRecord.getCreateTime(), DateUtils.PATTERN_YYYY_MM_DD_HH_MM_SS));
        vo.setPayNo(redPacketRecord.getPayNo());
        vo.setReceiveName(redPacketRecord.getReceiveName());
        vo.setReceiveNo(redPacketRecord.getReceiveNo());
        vo.setAliUserId(redPacketRecord.getAliUserId());
        vo.setReason(redPacketRecord.getReason());
        vo.setCollectAccountNumber(redPacketRecord.getCollectAccountNumber());
        vo.setAccountNo(redPacketRecord.getAccountNo());
        vo.setProvinceCode(redPacketRecord.getProvinceCode());
        vo.setProvinceName(redPacketRecord.getProvinceName());
        vo.setCashierPhoneNo(redPacketRecord.getCashierPhoneNo());
        vo.setCashierNo(redPacketRecord.getCashierNo());
        vo.setTradingTime(redPacketRecord.getTradingTime());
        vo.setPacketNo(redPacketRecord.getPacketNo());
        return vo;

    }
}
