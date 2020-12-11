package com.zanclick.redpacket.sdk.model;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * @Author: huze
 * @Date: 2020/12/10 15:37
 */
@Data
public class InvalidRedPacketModel extends  ZcApiModel {
    private static final long serialVersionUID = -5244825373925038845L;

    @JSONField(name = "packet_no")
    @NotEmpty(message = "缺少红包好")
    private String packetNo;
}
