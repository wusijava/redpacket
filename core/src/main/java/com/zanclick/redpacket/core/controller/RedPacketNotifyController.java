package com.zanclick.redpacket.core.controller;


import com.alibaba.fastjson.JSON;
import com.zanclick.redpacket.common.base.controller.BaseController;
import com.zanclick.redpacket.common.utils.DataUtils;
import com.zanclick.redpacket.core.dto.SettleResultData;
import com.zanclick.redpacket.core.dto.SettleResultDto;
import com.zanclick.redpacket.core.entity.RedPacket;
import com.zanclick.redpacket.core.entity.RedPacketRecord;
import com.zanclick.redpacket.core.service.RedPacketRecordService;
import com.zanclick.redpacket.core.service.RedPacketService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Date;

/**
 * 红包回调
 */
@Slf4j
@Api(description = "第三方放get请求调用接口")
@RestController("commission_notify")
@RequestMapping(value = "/api/open/rebate")
public class RedPacketNotifyController extends BaseController {

    @Autowired
    private RedPacketRecordService redPacketRecordService;
    @Autowired
    private RedPacketService redPacketService;

    @ApiOperation(value = "乐薪酬金回调地址")
    @PostMapping(value = "/notify")
    @ResponseBody
    public String notify(HttpServletRequest request) throws IOException {
        String jsonObj = getRequestString(request);
        log.error("乐薪异步通知:{}", jsonObj);
        try {
            if (DataUtils.isEmpty(jsonObj)) {
                log.error("乐薪回调请求参数不完整:{}", jsonObj);
                return "fail";
            }
            SettleResultDto settleResultDto = JSON.parseObject(jsonObj, SettleResultDto.class);
            if (DataUtils.isEmpty(settleResultDto)) {
                log.error("乐薪回调请求参数不完整:{}", jsonObj);
                return "fail";
            }
            log.error("回调数据{}", settleResultDto.toString());
            SettleResultData data = settleResultDto.getData();
            String orderId = data.getOrderId();
            RedPacketRecord redPacketRecord = redPacketRecordService.findByOutTradeNo(orderId);
            if (DataUtils.isEmpty(redPacketRecord)) {
                log.error("酬金打款记录未创建,requestNo:{}", orderId);
                return "fail";
            }
            RedPacket redPacket = redPacketService.selectBybrwOrdNo(redPacketRecord.getBrwOrdNo());
            if (DataUtils.isEmpty(redPacket)) {
                log.error("酬金未创建,brwOrdNo:{}", redPacket.getBrwOrdNo());
                return "fail";
            }
            if (data.isSuccess()) {
                //  回调返回成功——>修改酬金记录表状态成功 原因和状态——>酬金表修改状态为成功
                redPacketRecord.setState(RedPacketRecord.State.SUCCESS.getCode());
                redPacketRecord.setTradingTime(new Date());
                redPacketRecord.setReason("");
                redPacket.setState(RedPacket.State.SUCCESS.getCode());
                redPacket.setTradingTime(new Date());
            } else {
                //  回调返回失败——>修改酬金记录表状态失败 原因和状态——>酬金表修改状态为打款失败
                redPacketRecord.setState(RedPacketRecord.State.FAIL.getCode());
                //TODO 失败原因取那个字段不清楚
                redPacketRecord.setReason("打款失败,原因:" + data.getStatusMessage());
                redPacket.setState(RedPacket.State.FAIL.getCode());
            }
            redPacketRecordService.updateById(redPacketRecord);
            redPacketService.updateById(redPacket);
            return "success";
        } catch (Exception e) {
            log.error("乐薪异步回调异常:{}", e);
            return "fail";
        }

    }

}
