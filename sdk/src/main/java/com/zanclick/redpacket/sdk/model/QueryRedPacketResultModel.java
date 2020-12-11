package com.zanclick.redpacket.sdk.model;

import lombok.Data;

/**
 * @Author: huze
 * @Date: 2020/12/10 15:37
 */
@Data
public class QueryRedPacketResultModel extends ZcApiModel {
    private static final long serialVersionUID = 2115651166556462458L;
    /**
     * 红包状态
     */
    private String stateDesc;

    private String amount;

    private String packetNo;

    private String outTradeNo;


}
