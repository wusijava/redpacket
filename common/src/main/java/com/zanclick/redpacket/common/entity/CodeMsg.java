package com.zanclick.redpacket.common.entity;

/**
 * 网关返回码
 */
public enum CodeMsg {
    ERROR("10500", "系统异常"),
    EXPIRE("40015", "登陆过期"),
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
