package com.zanclick.redpacket.core.service;

import com.zanclick.redpacket.common.base.service.BaseService;
import com.zanclick.redpacket.core.entity.TransferRecord;
import com.zanclick.redpacket.core.mapper.TransferRecordMapper;

/**
 * @author wusi
 * @date 2020-12-07 14:33:25
 **/
public interface TransferRecordService extends BaseService<TransferRecord,Long> {
    TransferRecord findByPacketNo(String packetNo);
}
