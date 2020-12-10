package com.zanclick.redpacket.sdk.model;

import lombok.Data;

import java.lang.reflect.Parameter;

/**
 * @Author: huze
 * @Date: 2020/12/10 15:38
 */
@Data
public class InvalidRedPacketResultModel extends ZcApiModel {
    private static final long serialVersionUID = -517042826688675090L;

    private String result;

    private String packetNo;

    private String outTradeNo;
}
