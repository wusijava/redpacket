package com.zanclick.redpacket.core.lx.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 和包实时收单信息
 *
 * @author jia
 * @createTime 2020-03-19
 * @description order-realtime
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HbOrderRequest {

    /**
     * 商户订单号，由商户保持唯⼀性(必填)，64个英⽂字符以内
     */
    private String orderId;

    /**
     * 商户编码(必填)
     */
    private String dealerId;

    /**
     * 用户姓名（营业员姓名）
     */
    private String realName;

    /**
     * 收款账号（营业员收款手机号）
     */
    private String targetAccountNo;

    /**
     * ⽤户或联系⼈⼿机号(选填)
     */
    private String phoneNo;

    /**
     * 打款⾦额(单位为元, 必填)
     */
    private String pay;

    /**
     * 和包贷借款订单号
     */
    private String mplOrdNo;

    /**
     * 和包贷借款订单日期
     */
    private String mplOrdDt;

    /**
     * 省份编码
     */
    private String addressCode;

    private String dataList;

}
