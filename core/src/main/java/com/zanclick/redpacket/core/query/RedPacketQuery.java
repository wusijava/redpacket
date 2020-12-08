package com.zanclick.redpacket.core.query;

import com.zanclick.redpacket.core.entity.RedPacket;
import lombok.Data;

/**
 * @Author: huze
 * @Date: 2020/12/8 10:00
 */
@Data
public class RedPacketQuery extends RedPacket {

    private Integer page;

    private Integer limit;

    private String startTime;

    private String endTime;

    private String offset;
}
