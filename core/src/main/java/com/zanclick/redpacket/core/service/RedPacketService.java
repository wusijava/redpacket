package com.zanclick.redpacket.core.service;

import com.zanclick.redpacket.common.base.service.BaseService;
import com.zanclick.redpacket.core.entity.RedPacket;
import com.zanclick.redpacket.core.entity.Wallet;

/**
 * @author 27720
 * @date 2020-12-08 10:20:18
 **/
public interface RedPacketService extends BaseService<RedPacket,Long> {


    String selectSunAmount(RedPacket redPacket);

    RedPacket selectBybrwOrdNo(String outTradeNo);


    void transfer(Wallet query, String money);
}
