package com.zanclick.redpacket.core.vo;

import lombok.Data;

import java.util.Date;

/**
 * @ Description   :  红包详情VO
 * @ Author        :  wusi
 * @ CreateDate    :  2020/12/8$ 10:17$
 */
@Data
public class RedPacketVo {

    private Long id;
    /**
     * 红包金额
     */
    private String amount;
    /**
     * 订单号
     */
    private String tradeNo;
    /***
     * 外部订单号
     */
    private String outTradeNo;
    /**
     * 红包标题
     */
    private String title;
    /**
     * 应用id
     */
    private String appId;
    /**
     * 状态 1已创建 2已领取 3已到账 4已取消
     */
    private Integer state;
    /**
     * 类型 1支付宝 2乐薪 3 网商
     */
    private Integer type;
    /**
     * 商户号
     */
    private String merchantNo;
    /**
     * 收银员手机号
     */
    private String cashierPhoneNo;
    /**
     * 收款账号
     */
    private String sellerNo;
    /**
     * 顾客手机号
     */
    private String customPhone;
    /**
     * 创建时间
     */
    private Date createTime;


    private String orderTime;

    private Integer delayDays;

    private Integer isNextMonthSettle;
    /**
     * 红包领取账号
     */
    private String collectAccountNumber;

    /**
     * 到账账号
     */
    private String accountNo;

    /**
     * 省份
     */
    private String provinceName;

    /**
     * 下单营业员编号
     */
    private String cashierNo;

    private String stateDesc;

    private Date tradingTime;

}