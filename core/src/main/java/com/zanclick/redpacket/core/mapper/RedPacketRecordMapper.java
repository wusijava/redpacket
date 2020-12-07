package com.zanclick.redpacket.core.mapper;

import com.zanclick.redpacket.common.base.dao.mybatis.BaseMapper;
import com.zanclick.redpacket.core.entity.RedPacketRecord;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author wusi
 * @date 2020-12-07 14:27:02
 **/
@Mapper
public interface RedPacketRecordMapper extends BaseMapper<RedPacketRecord,Long> {


}
