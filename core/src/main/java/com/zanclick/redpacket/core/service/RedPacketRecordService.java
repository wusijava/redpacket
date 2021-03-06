package com.zanclick.redpacket.core.service;

import com.zanclick.redpacket.common.base.service.BaseService;
import com.zanclick.redpacket.core.entity.RedPacket;
import com.zanclick.redpacket.core.entity.RedPacketRecord;

import java.util.List;

/**
 * @author admin
 * @date 2020-12-08 17:00:50
 **/
public interface RedPacketRecordService extends BaseService<RedPacketRecord,Long> {

    /**
     * 酬金打款
     * @param redPacket
     * @return
     */
    Boolean settle(RedPacket redPacket);

    RedPacketRecord createRebateRecord(RedPacket rebate,Integer state);
    RedPacketRecord findByPacketNo(String packetNo);

    RedPacketRecord findByOutTradeNo(String outTradeNo);


    /**
     * 全部打款
     *
     * @param list
     */
    void allSettle(List<RedPacket> list);
}
