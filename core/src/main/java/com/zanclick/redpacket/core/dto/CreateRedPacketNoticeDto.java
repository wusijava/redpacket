package com.zanclick.redpacket.core.dto;

import lombok.Data;

/**
 * @Author: huze
 * @Date: 2020/12/10 17:28
 */
@Data
public class CreateRedPacketNoticeDto {

    private String packetNo;

    private String stateDesc;

    private String outTradeNo;

    private String amount;

}
