package com.zanclick.redpacket.core.listener;

import com.alibaba.fastjson.JSONObject;
import com.zanclick.redpacket.common.jms.JmsMessaging;
import com.zanclick.redpacket.common.utils.DataUtils;
import com.zanclick.redpacket.common.utils.DateUtils;
import com.zanclick.redpacket.core.entity.RedPacket;
import com.zanclick.redpacket.core.entity.RedPacketRecord;
import com.zanclick.redpacket.core.service.RedPacketRecordService;
import com.zanclick.redpacket.core.service.RedPacketService;
import com.zanclick.redpacket.user.entity.User;
import com.zanclick.redpacket.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;

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
    @Autowired
    private UserService userService;

    @JmsListener(destination = JmsMessaging.RECEIVE_REDPACKET_MESSAGE)
    public void getMessage(String message) {
        System.out.println("进入队列");
        JSONObject object = JSONObject.parseObject(message);
        Long id = object.getLong("id");
        String userName = object.getString("userName");
        User user = userService.findByUsername(userName);
        if (DataUtils.isEmpty(user)) {
            log.error("红包领取人异常,:{}", userName);
            return;
        }
        RedPacket packet = redPacketService.queryById(id);
        if (!RedPacket.State.WAITING.getCode().equals(packet.getState())) {
            log.error("红包状态异常,红包id:{}", id);
            return;
        }
        /*RedPacketRecord record= new RedPacketRecord();
        record.setPacketNo(packet.getPacketNo());
        RedPacketRecord redPacketRecord = redPacketRecordService.queryOne(record);
        if(RedPacketRecord.State.RECEIVED.getCode().equals(redPacketRecord.getState())||RedPacketRecord.State.SUCCESS.getCode().equals(redPacketRecord.getState())){
            log.error("红包已领取,红包id:{}", id);
            return;
        }
        if(RedPacketRecord.State.CANCLE.getCode().equals(redPacketRecord.getState())){
            log.error("红包已取消,红包id:{}", id);
            return;
        }*/
        RedPacketRecord createRecord = CreateRecord(user, packet);


    }

    private RedPacketRecord CreateRecord(User user, RedPacket packet) {
        RedPacketRecord redPacketRecord = new RedPacketRecord();
        redPacketRecord.setAmount(packet.getAmount());
        redPacketRecord.setTradeNo(packet.getTradeNo());
        redPacketRecord.setOutTradeNo(packet.getOutTradeNo());
        redPacketRecord.setTitle(packet.getTitle());
        redPacketRecord.setAppId(packet.getAppId());
        //判断是否延期到账 或隔月到账
        if (packet.getDelayDays().equals(0) && packet.getIsNextMonthSettle().equals(0)) {
            redPacketRecord.setState(RedPacketRecord.State.SUCCESS.getCode());
            packet.setState(RedPacket.State.SUCCESS.getCode());
        }
        //设置隔月到账
        if (packet.getIsNextMonthSettle().equals(1)) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(packet.getOrderTime());
            calendar.set(Calendar.DAY_OF_MONTH, 1);
            calendar.add(Calendar.MONTH, 1);
            compareDate(calendar.getTime(),redPacketRecord,packet);

        }
        //设置了延期到账
        if (packet.getIsNextMonthSettle().equals(0)) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(packet.getOrderTime());
            calendar.add(Calendar.DAY_OF_MONTH, packet.getDelayDays());
            compareDate(calendar.getTime(), redPacketRecord,packet);
        }
        redPacketRecord.setType(packet.getType());
        redPacketRecord.setMerchantNo(packet.getMerchantNo());
        redPacketRecord.setCreateTime(new Date());
        redPacketRecord.setSellerNo(packet.getSellerNo());
        redPacketRecord.setPacketNo(packet.getPacketNo());
        redPacketRecord.setCashierPhoneNo(packet.getCashierPhoneNo());
        redPacketRecord.setUserName(user.getUsername());
        redPacketRecordService.insert(redPacketRecord);
        redPacketService.updateById(packet);

        return null;
    }

    private void compareDate(Date time, RedPacketRecord redPacketRecord,RedPacket packet) {
        Date now = new Date();
        boolean boo = now.getTime() > time.getTime();
        if (boo) {
            redPacketRecord.setState(RedPacketRecord.State.SUCCESS.getCode());
            packet.setState(RedPacket.State.SUCCESS.getCode());
        } else {
            redPacketRecord.setState(RedPacketRecord.State.RECEIVED.getCode());
            packet.setState(RedPacket.State.RECEIVED.getCode());
        }
    }


}
