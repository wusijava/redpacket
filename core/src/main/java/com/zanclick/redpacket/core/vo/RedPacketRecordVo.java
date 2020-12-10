package com.zanclick.redpacket.core.vo;

import lombok.Data;

import java.util.Date;

/**
 * @Author: huze
 * @Date: 2020/12/8 14:24
 */
@Data
public class RedPacketRecordVo {

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
    /**
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

    private String stateDesc;
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
     * 商户收款账号
     */
    private String sellerNo;
    /**
     * 创建时间
     */
    private String createTime;
    /**
     * 付款账号
     */
    private String payNo;
    /**
     * 收款人姓名
     */
    private String receiveName;
    /**
     * 收款人账号
     */
    private String receiveNo;
    /**
     * 收款人uid
     */
    private String aliUserId;
    /**
     * 领取失败原因
     */
    private String reason;
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


    /**
     * 省份
     */
    private String provinceCode;
    /**
     * 交易时间
     */
    private Date tradingTime;

    private String packetNo;

    private String customPhone;

    public static String[] headers = {
            "序号",
            "订单号",
            "授权单号",
            "交易时间",
            "省份",
            "下单营业员编号",
            "下单营业员手机号",
            "办理手机号",
            "到账账号",
            "红包领取账号",
            "酬金金额",
            "套餐标题",
            "打款状态",
            "创建时间",
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
            "accountNo",
            "collectAccountNumber",
            "amount",
            "title",
            "stateDesc",
            "createTime",
    };

}
