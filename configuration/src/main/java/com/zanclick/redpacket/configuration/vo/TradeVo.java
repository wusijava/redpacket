package com.zanclick.redpacket.configuration.vo;

import lombok.Data;

import java.util.Date;

/**
 * @author huze
 * @title: Trade
 * @projectName dz-riskgo
 * @description: TODO
 * @date 2020/9/1115:58
 */
@Data
public class TradeVo {

    private String pid;
    /**
     * //业务id
     */
    private String appId;

    /**
     * //商户名称
     */
    private String merchantName;
    /**
     * //商户编号
     */
    private String merchantNo;
    /**
     * //订单号
     */
    private String orderNo;

    /**
     * //交易期数
     */
    private Integer num;
    /**
     * //冻结金额
     */
    private String amount;
    /**
     * //结算金额
     */
    private String settleAmount;
    /**
     * //每期应还金额
     */
    private String eachMoney;
    /**
     * 收款账号
     */
    private String sellerNo;

    private Date createTime;


}
