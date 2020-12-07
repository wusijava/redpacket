package com.zanclick.redpacket.sdk.enums;


/**
 * @author duchong
 * @description 对外接口枚举
 * 方法命名规则  统一前缀.模块名称.模块内操作对象泛称.执行操作
 * 统一前缀 com.zanclick.zcpay
 * @date 2020-8-22 11:29:45
 */
public enum ApiMethod {
    RISK_MERCHANT_CHECK("com.zanclick.riskgo.merchant.risk.merchant", "风险商户校验接口", "1.0"),
    RISK_TRADE_QUERY("com.zanclick.riskgo.risk.trade.query", "风险查询接口", "1.0"),
    RISK_STATEMENT("com.zanclick.riskgo.risk.risk.statement", "风险申诉接口", "1.0"),
    COMPLAINT_INQUIRY("com.zanclick.riskgo.risk.complaint.inquiry", "风险申诉查询接口", "1.0"),
    RISK_TRADE_PUSH_NOTICE("com.zanclick.riskgo.risk.trade.push.notice", "风险推送处理通知", "1.0"),
    RISK_CLAIM_PROCESS_NOTICE("com.zanclick.riskgo.risk.slaim.process.notice", "风险申诉处理通知", "1.0");

    public String method;

    public String desc;

    public String version;

    ApiMethod(String method, String desc, String version) {
        this.method = method;
        this.desc = desc;
        this.version = version;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}
