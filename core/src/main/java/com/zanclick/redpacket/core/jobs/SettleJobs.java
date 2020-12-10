package com.zanclick.redpacket.core.jobs;

import com.zanclick.redpacket.common.cache.annotation.ZcCache;
import com.zanclick.redpacket.common.utils.DataUtils;
import com.zanclick.redpacket.common.utils.MoneyUtils;
import com.zanclick.redpacket.core.entity.RedPacket;
import com.zanclick.redpacket.core.entity.RedPacketRecord;
import com.zanclick.redpacket.core.entity.Wallet;
import com.zanclick.redpacket.core.service.RedPacketRecordService;
import com.zanclick.redpacket.core.service.RedPacketService;
import com.zanclick.redpacket.core.service.WalletService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 * @ Description   :  已领红包未到账的用定时任务轮训是否到账
 * @ Author        :  wusi
 * @ CreateDate    :  2020/12/10$ 11:00$
 */
@Slf4j
@Component
public class SettleJobs {
    @Autowired
    private RedPacketRecordService redPacketRecordService;
    @Autowired
    private RedPacketService redPacketService;
    @Autowired
    private WalletService walletService;

    @Scheduled(cron = "0 32 11 * * ?")
    public void cronJobs() {
        log.error("定时查找红包是否可以到账定时任务启动!");
        RedPacketRecord redPacketRecord = new RedPacketRecord();
        //查找已领取 未到账红包记录
        redPacketRecord.setState(2);
        List<RedPacketRecord> redPacketRecords = redPacketRecordService.queryList(redPacketRecord);
        for (RedPacketRecord packet : redPacketRecords) {
            Date now = new Date();
            boolean boo = now.getTime() >= packet.getArrivalTime().getTime();
            if(boo){
                //增加红包可提现金额
                Wallet byUserName = walletService.findByUserName(packet.getUserName());
                byUserName.setCanWithdrawAmount(MoneyUtils.add(byUserName.getCanWithdrawAmount(), packet.getAmount()));
                walletService.updateById(byUserName);
                packet.setState(3);
                redPacketRecordService.updateById(packet);
                //更新红包状态
               RedPacket byPacketNo = redPacketService.findByPacketNo(packet.getPacketNo());
               if(DataUtils.isNotEmpty(byPacketNo)){
                   byPacketNo.setState(3);
                   redPacketService.updateById(byPacketNo);
               }
            }
        }
    }
}
