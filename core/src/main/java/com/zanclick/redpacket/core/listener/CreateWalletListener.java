package com.zanclick.redpacket.core.listener;

import com.zanclick.redpacket.common.jms.JmsMessaging;
import com.zanclick.redpacket.core.entity.Wallet;
import com.zanclick.redpacket.core.service.WalletService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @ Description   :  注册成功后初始化钱包 (循环依赖 采用队列处理)
 * @ Author        :  wusi
 * @ CreateDate    :  2020/12/9$ 11:34$
 */
@Component
@Slf4j
public class CreateWalletListener {
    @Autowired
    private WalletService walletService;

    @JmsListener(destination = JmsMessaging.CREATE_WALLET_MESSAGE)
    public void getMessage(String message) {
        Wallet wallet=new Wallet();
        wallet.setCreateTime(new Date());
        wallet.setTotalAmount("0");
        wallet.setCanWithdrawAmount("0");
        wallet.setUserName(message);
        wallet.setState(1);
        try {
            walletService.insert(wallet);
        } catch (Exception e) {
            log.error("初始化钱包异常,{}", message);
        }
    }
}
