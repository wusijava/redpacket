package com.zanclick.redpacket.core.service.impl;

import com.zanclick.redpacket.common.base.dao.mybatis.BaseMapper;
import com.zanclick.redpacket.common.base.service.impl.BaseMybatisServiceImpl;
import com.zanclick.redpacket.core.entity.Wallet;
import com.zanclick.redpacket.core.mapper.WalletMapper;
import com.zanclick.redpacket.core.service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author wusi
 * @date 2020-12-07 14:30:32
 **/
@Service
public class WalletServiceImpl extends BaseMybatisServiceImpl<Wallet,Long> implements WalletService {

    @Autowired
    private WalletMapper walletMapper;


    @Override
    protected BaseMapper<Wallet, Long> getBaseMapper() {
        return walletMapper;
    }

    @Override
    public Wallet findByUserName(String userName) {
        return walletMapper.findByUserName(userName);
    }
}
