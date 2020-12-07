package com.zanclick.redpacket.common.model;

/**
 * 网关返回码
 */
public enum CodeMsg{
    SYSTEM_ERROR("10500", "系统异常"),
    INVALID_LOGIN("40015", "登陆过期"),
    INVALID_VERSION("30015", "版本过期"),
    FAIL("40500", "操作失败"),
    SUCCESS("20000", "操作成功");

    CodeMsg(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    private String code;

    private String msg;

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

}
