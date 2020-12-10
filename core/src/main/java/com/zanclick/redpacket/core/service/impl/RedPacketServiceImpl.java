package com.zanclick.redpacket.core.service.impl;

import com.zanclick.redpacket.common.base.dao.mybatis.BaseMapper;
import com.zanclick.redpacket.common.base.service.impl.BaseMybatisServiceImpl;
import com.zanclick.redpacket.common.utils.DataUtils;
import com.zanclick.redpacket.core.entity.RedPacket;
import com.zanclick.redpacket.core.mapper.RedPacketMapper;
import com.zanclick.redpacket.core.service.RedPacketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author 27720
 * @date 2020-12-08 10:20:18
 **/
@Service
public class RedPacketServiceImpl extends BaseMybatisServiceImpl<RedPacket,Long> implements RedPacketService {

    @Autowired
    private RedPacketMapper redPacketMapper;


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
    public RedPacket selectByOutTradeNo(String outTradeNo) {
        return redPacketMapper.selectByOutTradeNo(outTradeNo);
    }
}
