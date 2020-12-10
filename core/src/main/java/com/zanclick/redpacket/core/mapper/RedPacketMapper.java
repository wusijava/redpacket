package com.zanclick.redpacket.core.mapper;

import com.zanclick.redpacket.common.base.dao.mybatis.BaseMapper;
import com.zanclick.redpacket.core.entity.RedPacket;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author admin
 * @date 2020-12-10 10:23:51
 **/
@Mapper
public interface RedPacketMapper extends BaseMapper<RedPacket,Long> {


    String selectSunAmount(RedPacket redPacket);

    RedPacket selectBybrwOrdNo(String brwOrdNo);
    RedPacket findByPacketNo(String packetNo);

    RedPacket selectByOutTradeNo(String outTradeNo);
}
