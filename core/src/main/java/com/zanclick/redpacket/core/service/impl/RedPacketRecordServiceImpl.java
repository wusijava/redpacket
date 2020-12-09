package com.zanclick.redpacket.core.service.impl;

import com.zanclick.redpacket.common.base.dao.mybatis.BaseMapper;
import com.zanclick.redpacket.common.base.service.impl.BaseMybatisServiceImpl;
import com.zanclick.redpacket.core.entity.RedPacketRecord;
import com.zanclick.redpacket.core.mapper.RedPacketRecordMapper;
import com.zanclick.redpacket.core.service.RedPacketRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author wusi
 * @date 2020-12-07 14:27:02
 **/
@Service
public class RedPacketRecordServiceImpl extends BaseMybatisServiceImpl<RedPacketRecord,Long> implements RedPacketRecordService {

    @Autowired
    private RedPacketRecordMapper redPacketRecordMapper;


    @Override
    protected BaseMapper<RedPacketRecord, Long> getBaseMapper() {
        return redPacketRecordMapper;
    }

    @Override
    public RedPacketRecord findByPacketNo(String packetNo) {
        return redPacketRecordMapper.findByPacketNo(packetNo);
    }
}
