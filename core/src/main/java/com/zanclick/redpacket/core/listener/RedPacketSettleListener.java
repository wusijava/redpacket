package com.zanclick.redpacket.core.listener;

import com.alibaba.fastjson.JSONObject;

import com.zanclick.redpacket.common.queue.contents.RedPacketContents;
import com.zanclick.redpacket.common.queue.listener.BaseListener;
import com.zanclick.redpacket.common.utils.DataUtils;
import com.zanclick.redpacket.core.entity.RedPacket;
import com.zanclick.redpacket.core.service.RedPacketRecordService;
import com.zanclick.redpacket.core.service.RedPacketService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

/**
 * @Author: mapei
 * @Date: 2020/9/25 11:32
 * 对酬金进行打款   和包支付实时下单
 */
@Slf4j
@Component
public class RedPacketSettleListener extends BaseListener {

    @Autowired
    private RedPacketService redPacketService;

    @Autowired
    private RedPacketRecordService redPacketRecordService;

    @JmsListener(destination = RedPacketContents.REDPACKET_SETTLE_MESSAGE)
    public void getMessage(String message) {
        log.error("开始进行酬金打款, outTradeNo:{}", message);
        JSONObject object = JSONObject.parseObject(message);
        String outTradeNo = object.getString("outTradeNo");
        if (DataUtils.isEmpty(outTradeNo)) {
            log.error("和包借款订单号错误:{}", outTradeNo);
            return;
        }
        try {
            RedPacket redPacket = redPacketService.selectByOutTradeNo(outTradeNo);
            if (DataUtils.isEmpty(redPacket)) {
                log.error("酬金打款失败,数据不存在:{}", outTradeNo);
                return;
            }
            if (redPacket.isSuccess()) {
                log.error("酬金打款失败,已打款成功:{}", outTradeNo);
                return;
            }
            if (redPacket.isWaiting()) {
                log.error("酬金打款失败,正在打款中:{}", outTradeNo);
                return;
            }
            if (redPacket.isRefund()) {
                log.error("酬金打款失败,用户已退单:{}", outTradeNo);
                return;
            }
            redPacketRecordService.settle(redPacket);
        } catch (Exception e) {
            log.error("酬金打款异常:{},{}", outTradeNo, e);
            //super.sendMsgAgain(object, RebateContents.REBATE_SETTLE_MESSAGE,true);
        }
    }
}
