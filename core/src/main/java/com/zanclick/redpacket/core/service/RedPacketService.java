package com.zanclick.redpacket.core.service;

import com.zanclick.redpacket.common.base.service.BaseService;
import com.zanclick.redpacket.core.entity.RedPacket;

/**
 * @author 27720
 * @date 2020-12-08 10:20:18
 **/
public interface RedPacketService extends BaseService<RedPacket,Long> {


    String selectSunAmount(RedPacket redPacket);

    RedPacket selectByOutTradeNo(String outTradeNo);

}
