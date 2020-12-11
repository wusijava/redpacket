package com.zanclick.redpacket.core.listener;

import com.alibaba.fastjson.JSONObject;
import com.zanclick.redpacket.common.jms.JmsMessaging;
import com.zanclick.redpacket.common.utils.DataUtils;
import com.zanclick.redpacket.common.utils.DateUtils;
import com.zanclick.redpacket.common.utils.MoneyUtils;
import com.zanclick.redpacket.core.entity.RedPacket;
import com.zanclick.redpacket.core.entity.RedPacketRecord;
import com.zanclick.redpacket.core.entity.TransferRecord;
import com.zanclick.redpacket.core.entity.Wallet;
import com.zanclick.redpacket.core.service.RedPacketRecordService;
import com.zanclick.redpacket.core.service.RedPacketService;
import com.zanclick.redpacket.core.service.TransferRecordService;
import com.zanclick.redpacket.core.service.WalletService;
import com.zanclick.redpacket.user.entity.User;
import com.zanclick.redpacket.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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
    @Autowired
    private WalletService walletService;
    @Autowired
    private TransferRecordService transferRecordService;

    @JmsListener(destination = JmsMessaging.RECEIVE_REDPACKET_MESSAGE)
    public void getMessage(String message) throws ParseException {
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
        RedPacketRecord redPacketRecord = redPacketRecordService.findByPacketNo(packet.getPacketNo());
        if(DataUtils.isNotEmpty(redPacketRecord)&&(RedPacketRecord.State.RECEIVED.getCode().equals(redPacketRecord.getState())||RedPacketRecord.State.SUCCESS.getCode().equals(redPacketRecord.getState()))){
            log.error("红包已领取,红包id:{}", id);
            return;
        }
        if(DataUtils.isNotEmpty(redPacketRecord)&&(RedPacketRecord.State.CANCLE.getCode().equals(redPacketRecord.getState()))){
            log.error("红包已取消,红包id:{}", id);
            return;
        }
        RedPacketRecord createRecord = CreateRecord(user, packet);


    }

    private RedPacketRecord CreateRecord(User user, RedPacket packet) throws ParseException {
        Wallet byUserName = walletService.findByUserName(user.getUsername());
        RedPacketRecord redPacketRecord = new RedPacketRecord();
        redPacketRecord.setAmount(packet.getAmount());
        redPacketRecord.setTradeNo(packet.getTradeNo());
        redPacketRecord.setOutTradeNo(packet.getOutTradeNo());
        redPacketRecord.setTitle(packet.getTitle());
        redPacketRecord.setAppId(packet.getAppId());
        //判断是否延期到账 或隔月到账
        if (packet.getDelayDays().equals(0) && packet.getIsNextMonthSettle().equals(0)) {
            redPacketRecord.setState(RedPacketRecord.State.SUCCESS.getCode());
            //未设置延期和隔月
            redPacketRecord.setArrivalTime(packet.getOrderTime());
            packet.setState(RedPacket.State.SUCCESS.getCode());
            //增加钱包金额
            if(DataUtils.isNotEmpty(byUserName)){
                byUserName.setCanWithdrawAmount(MoneyUtils.add(byUserName.getCanWithdrawAmount(),packet.getAmount()));
                byUserName.setTotalAmount(MoneyUtils.add(byUserName.getTotalAmount(),packet.getAmount()));
            }
            //新增转账记录
            createTransferRecord(user.getUsername(),packet.getAmount(),1,packet.getPacketNo(),packet.getTradeNo());
        }
        //设置隔月到账
        if (packet.getIsNextMonthSettle().equals(1)) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(packet.getOrderTime());
            calendar.set(Calendar.DAY_OF_MONTH, 1);
            calendar.add(Calendar.MONTH, 1);
            compareDate(calendar.getTime(),redPacketRecord,packet,byUserName);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date arrivalDate = sdf.parse(DateUtils.formatDate(calendar.getTime(), "yyyy-MM-dd"));
            redPacketRecord.setArrivalTime(arrivalDate);
        }
        //设置了延期到账
        if (!packet.getDelayDays().equals(0)) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(packet.getOrderTime());
            calendar.add(Calendar.DAY_OF_MONTH, packet.getDelayDays());
            compareDate(calendar.getTime(), redPacketRecord,packet,byUserName);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date arrivalDate = sdf.parse(DateUtils.formatDate(calendar.getTime(), "yyyy-MM-dd"));
            redPacketRecord.setArrivalTime(arrivalDate);
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
        walletService.updateById(byUserName);

        return null;
    }

    private void createTransferRecord(String userName,String amount,Integer state,String packetNo,String tradeNo) {
        //1为领取到钱包
        TransferRecord record=new TransferRecord();
        record.setType("1");
        record.setUserName(userName);
        record.setAmount(amount);
        record.setCreateTime(new Date());
        record.setState(state);
        record.setPacketNo(packetNo);
        record.setTradeNo(tradeNo);
        transferRecordService.insert(record);
    }

    private void compareDate(Date time, RedPacketRecord redPacketRecord,RedPacket packet,Wallet wallet) throws ParseException {
        Date now = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date nowDate = sdf.parse(DateUtils.formatDate(now, "yyyy-MM-dd"));
        Date orderDate = sdf.parse(DateUtils.formatDate(time, "yyyy-MM-dd"));
        boolean boo = nowDate.getTime() >= orderDate.getTime();
        if (boo) {
            redPacketRecord.setState(RedPacketRecord.State.SUCCESS.getCode());
            packet.setState(RedPacket.State.SUCCESS.getCode());
            if(DataUtils.isNotEmpty(wallet)){
                wallet.setCanWithdrawAmount(MoneyUtils.add(wallet.getCanWithdrawAmount(),packet.getAmount()));
                wallet.setTotalAmount(MoneyUtils.add(wallet.getTotalAmount(),packet.getAmount()));
            }
            //新增转账记录
            createTransferRecord(wallet.getUserName(),packet.getAmount(),1,packet.getPacketNo(),packet.getTradeNo());

        } else {
            redPacketRecord.setState(RedPacketRecord.State.RECEIVED.getCode());
            packet.setState(RedPacket.State.RECEIVED.getCode());
            if(DataUtils.isNotEmpty(wallet)){
                wallet.setTotalAmount(MoneyUtils.add(wallet.getTotalAmount(),packet.getAmount()));
            }
            //新增转账记录
            createTransferRecord(wallet.getUserName(),packet.getAmount(),2,packet.getPacketNo(),packet.getTradeNo());
        }
    }


}
