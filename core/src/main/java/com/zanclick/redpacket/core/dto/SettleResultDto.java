package com.zanclick.redpacket.core.dto;

import lombok.Data;

/**
 * @Author: mapei
 * @Date: 2020/9/24 15:44
 */
@Data
public class SettleResultDto {

    private String notifyId;

    private String timestamp;

    private  SettleResultData data;

}
