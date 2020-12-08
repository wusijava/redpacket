package com.zanclick.redpacket.core.listener;

import com.zanclick.redpacket.common.jms.JmsMessaging;
import com.zanclick.redpacket.core.entity.RedPacket;
import com.zanclick.redpacket.core.entity.RedPacketRecord;
import com.zanclick.redpacket.core.service.RedPacketRecordService;
import com.zanclick.redpacket.core.service.RedPacketService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

/**
 * @ Description   :  红包领取
 * @ Author        :  wusi
 * @ CreateDate    :  2020/12/8$ 11:27$
 */
@Component
@Slf4j
public class ReceiveRedPacketListener {
    @Autowired
    private RedPacketService redPacketService;
    @Autowired
    private RedPacketRecordService redPacketRecordService;

    @JmsListener(destination = JmsMessaging.RECEIVE_REDPACKET_MESSAGE)
    public void getMessage(String message) {
        Long id = Long.valueOf(message);
        RedPacket packet = redPacketService.queryById(id);
        if(!RedPacket.State.WAITING.getCode().equals(packet.getState())){
                log.error("红包状态异常,红包id:{}", id);
                return;
        }
        RedPacketRecord record= new RedPacketRecord();



    }
}
