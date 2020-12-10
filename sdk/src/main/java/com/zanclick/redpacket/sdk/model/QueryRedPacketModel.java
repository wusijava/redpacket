package com.zanclick.redpacket.sdk.model;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * @Author: huze
 * @Date: 2020/12/10 15:36
 */
@Data
public class QueryRedPacketModel extends ZcApiModel{
    private static final long serialVersionUID = 5303462894690609149L;

    @JSONField(name = "packet_no")
    @NotEmpty(message = "缺少红包单号")
    private String packetNo;
}
