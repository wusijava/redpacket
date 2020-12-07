package com.zanclick.redpacket.common.enums;

/**
 * 异常枚举
 */
public enum NoticeType{
    HTTP(0, "Http请求"),
    MESSAGE(1, "队列消息");

    private Integer code;
    private String desc;

    NoticeType(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

}
