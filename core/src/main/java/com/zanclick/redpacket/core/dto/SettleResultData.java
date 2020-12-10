package com.zanclick.redpacket.core.dto;

import lombok.Data;

/**
 * @Author: mapei
 * @Date: 2020/9/29 15:49
 */
@Data
public class SettleResultData {
    /**
     * 实际打款金额，精确到两位
     * 小数，单位元
     */
    private String pay;
    /**
     * 请求打款金额，精确到两位
     * 小数，单位元
     */
    private String brokerAmount;
    /**
     * 系统环境
     */
    private String anchorId;
    /**
     * 代征主体，固定填写 chnl01
     */
    private String brokerId;

    /**
     * 打款目标账户
     */
    private String targetAccountNo;



    /**
     * 商户 ID
     */
    private String dealerId;

    /**
     * 用户身份证号
     */
    private String idCard;

    /**
     * 订单号
     */
    private String orderId;

    /**
     * 联系电话
     */
    private String phoneNo;

    /**
     * 用户真实姓名
     */
    private String realName;

    /**
     * 支付渠道
     */
    private String withdrawPlatform;

    /**
     * 税费
     */
    private String tax;

    /**
     * 服务费
     */
    private String brokerFee;

    /**
     * 状态描述
     */
    private String statusMessage;


    /**
     * 详细状态描述
     */
    private String statusDetailMessage;

    /**
     * 订单状态码
     */
    private String status;
    /**
     * 详细状态编码
     */
    private String statusDetail;
    /**
     * 交易处理说明
     */
    private String notes;


    /**
     * brokerId : LEXIN
     * notes : 管理员强制订单完成
     * withdrawPlatform : HB
     * orderId : HB20200327726
     * dealerId : MCH202003230013
     * idCard : 410881198908153547
     * targetAccountNo : 18757575460
     * pay : 0.01
     * statusDetail : 1
     * tax :
     * brokerFee : 0
     * anchorId : prod
     * phoneNo : 18757575460
     * statusMessage : 交易成功
     * realName : 苗洁
     * sysAmount : 0.00
     * statusDetailMessage : 交易成功
     * brokerAmount : 0.01
     * status : 1
     */


    public enum Status {
        success("1", "已打款"),
        fail("2", "打款失败"),
        wait("4", "待打款(暂停处理)");

        private String code;
        private String desc;

        Status(String code, String desc) {
            this.code = code;
            this.desc = desc;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }
    }

    public Boolean isSuccess() {
        return Status.success.getCode().equals(status);
    }

    public Boolean isFail() {
        return Status.fail.getCode().equals(status);
    }
}
