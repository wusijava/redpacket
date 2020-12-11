package com.zanclick.redpacket.core.service.impl;

import com.zanclick.redpacket.common.base.dao.mybatis.BaseMapper;
import com.zanclick.redpacket.common.base.service.impl.BaseMybatisServiceImpl;
import com.zanclick.redpacket.core.entity.TransferRecord;
import com.zanclick.redpacket.core.mapper.TransferRecordMapper;
import com.zanclick.redpacket.core.service.TransferRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author admin
 * @date 2020-12-11 10:06:51
 **/
@Service
public class TransferRecordServiceImpl extends BaseMybatisServiceImpl<TransferRecord,Long> implements TransferRecordService {

    @Autowired
    private TransferRecordMapper transferRecordMapper;


    @Override
    protected BaseMapper<TransferRecord, Long> getBaseMapper() {
        return transferRecordMapper;
    }

    @Override
    public TransferRecord findByPacketNo(String packetNo) {
        return transferRecordMapper.findByPacketNo(packetNo);
    }
}
