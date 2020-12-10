package com.zanclick.redpacket.core.dto;

import lombok.Data;

/**
 * @Author: huze
 * @Date: 2020/12/10 10:49
 * 业务明细 1必填
 */
@Data
public class DataList {


    /**
     * 办理该业务的订单号
     * 1
     */
    private String tradeId;
    /**
     * 酬金id
     * 2
     */
    private String rewardId;
    /**
     * 金额，精确两位小数，单位元
     * 1
     *
     */
    private String money;
    /**
     * 订单时间（该业务办理时间,
     * 格式为：yyyyMMdd）
     * 2
     */
    private String timestamp;

    /**
     * 业务类型
     * 2
     */
    private String businessType;
    /**
     * 业务名称
     * 1
     */
    private String businessName;
    /**
     * 业务办理手机号
     * 2
     */
    private String mobile;


}
