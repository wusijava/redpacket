package com.zanclick.redpacket.core.service;

import com.zanclick.redpacket.common.base.service.BaseService;
import com.zanclick.redpacket.core.entity.RedPacketRecord;

/**
 * @author admin
 * @date 2020-12-08 17:00:50
 **/
public interface RedPacketRecordService extends BaseService<RedPacketRecord,Long> {
    RedPacketRecord findByPacketNo(String packetNo);
}
