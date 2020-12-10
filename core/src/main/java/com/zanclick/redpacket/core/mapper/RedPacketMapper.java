package com.zanclick.redpacket.core.mapper;

import com.zanclick.redpacket.common.base.dao.mybatis.BaseMapper;
import com.zanclick.redpacket.core.entity.RedPacket;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author 27720
 * @date 2020-12-08 10:20:18
 **/
@Mapper
public interface RedPacketMapper extends BaseMapper<RedPacket,Long> {


    String selectSunAmount(RedPacket redPacket);

    RedPacket selectByOutTradeNo(String outTradeNo);
}
