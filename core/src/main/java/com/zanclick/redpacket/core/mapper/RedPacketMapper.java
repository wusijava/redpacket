package com.zanclick.redpacket.core.mapper;

import com.zanclick.redpacket.common.base.dao.mybatis.BaseMapper;
import com.zanclick.redpacket.core.entity.RedPacket;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author wusi
 * @date 2020-12-07 14:20:22
 **/
@Mapper
public interface RedPacketMapper extends BaseMapper<RedPacket,Long> {


}
