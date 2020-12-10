package com.zanclick.redpacket.core.service.impl;

import com.alipay.api.AlipayClient;
import com.alipay.api.response.AlipayFundTransToaccountTransferResponse;
import com.zanclick.redpacket.common.base.dao.mybatis.BaseMapper;
import com.zanclick.redpacket.common.base.service.impl.BaseMybatisServiceImpl;
import com.zanclick.redpacket.common.utils.DataUtils;
import com.zanclick.redpacket.common.utils.MoneyUtils;
import com.zanclick.redpacket.configuration.entity.AliPayConfiguration;
import com.zanclick.redpacket.configuration.service.AliPayConfigurationService;
import com.zanclick.redpacket.core.entity.RedPacket;
import com.zanclick.redpacket.core.entity.TransferRecord;
import com.zanclick.redpacket.core.entity.Wallet;
import com.zanclick.redpacket.core.mapper.RedPacketMapper;
import com.zanclick.redpacket.core.payDTO.Transfer;
import com.zanclick.redpacket.core.service.RedPacketService;
import com.zanclick.redpacket.core.service.TransferRecordService;
import com.zanclick.redpacket.core.service.WalletService;
import com.zanclick.redpacket.core.utils.AuthorizePayUtil;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * @author 27720
 * @date 2020-12-08 10:20:18
 **/
@Service
public class RedPacketServiceImpl extends BaseMybatisServiceImpl<RedPacket,Long> implements RedPacketService {

    @Autowired
    private RedPacketMapper redPacketMapper;
    @Autowired
    private AliPayConfigurationService aliPayConfigurationService;
    @Autowired
    private WalletService walletService;
    @Autowired
    private TransferRecordService transferRecordService;


    @Override
    protected BaseMapper<RedPacket, Long> getBaseMapper() {
        return redPacketMapper;
    }

    @Override
    public String selectSunAmount(RedPacket redPacket) {
        String amount = redPacketMapper.selectSunAmount(redPacket);
        if (DataUtils.isEmpty(amount)){
            amount = "0.00";
        }
        return amount;
    }

    @Override
    @Transactional
    public void transfer(Wallet query, String money) {
        AlipayClient client = aliPayConfigurationService.getClient(1L);
        Transfer transfer =new Transfer();
        transfer.setAmount(money);
        transfer.setPayee_account(query.getReceiveNo());
        transfer.setPayee_type("ALIPAY_LOGONID");
        transfer.setRemark("XXX的红包");
        transfer.setPayer_show_name("点赞科技有限公司");
        transfer.setPayee_real_name(query.getReceiveName());
        //AlipayFundTransToaccountTransferResponse response = AuthorizePayUtil.transfer(client, transfer);
        if (true) {
            query.setTotalAmount(MoneyUtils.subtract(query.getTotalAmount(),money));
            query.setCanWithdrawAmount(MoneyUtils.subtract(query.getCanWithdrawAmount(),money));
            walletService.updateById(query);
            TransferRecord record=new TransferRecord();
            record.setAmount(money);
            record.setCreateTime(new Date());
            record.setReceiveNo(query.getReceiveNo());
            record.setUserName(query.getUserName());
            //2为提现
            record.setType("2");
            transferRecordService.insert(record);
        }
    }

    @Override
    public RedPacket findByPacketNo(String packetNo) {
        return redPacketMapper.findByPacketNo(packetNo);
    }
}
