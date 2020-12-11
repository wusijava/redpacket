package com.zanclick.redpacket.core.mapper;

import com.zanclick.redpacket.common.base.dao.mybatis.BaseMapper;
import com.zanclick.redpacket.core.entity.TransferRecord;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author wusi
 * @date 2020-12-07 14:33:25
 **/
@Mapper
public interface TransferRecordMapper extends BaseMapper<TransferRecord,Long> {
    TransferRecord findByPacketNo(String packetNo);

}
