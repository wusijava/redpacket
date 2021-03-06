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

    private Integer index;

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
    private String createTime;


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

    private String tradingTime;

    private String typeDesc;

    /**
     * 借款订单号
     */
    private String brwOrdNo;



    public static String[] headers = {
            "序号",
            "订单号",
            "授权单号",
            "交易时间",
            "省份",
            "下单营业员编号",
            "下单营业员手机号",
            "办理手机号",
            "酬金金额",
            "套餐标题",
            "创建时间",
            "打款状态",
    };

    public static String[] keys = {
            "index",
            "tradeNo",
            "outTradeNo",
            "orderTime",
            "provinceName",
            "cashierNo",
            "cashierPhoneNo",
            "customPhone",
            "amount",
            "title",
            "createTime",
            "stateDesc",
    };
}
