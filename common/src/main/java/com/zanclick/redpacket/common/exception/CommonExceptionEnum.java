package com.zanclick.redpacket.common.exception;

import com.zanclick.redpacket.common.enums.EnumInterface;

/**
 * 异常枚举
 */
public enum CommonExceptionEnum implements EnumInterface {
    SYSTEM_ERROR("SYSTEM_ERROR", "系统异常"),
    INVALID_REQUEST_ERROR("INVALID_REQUEST_ERROR", " 请求方式 {0} 错误 ! 请使用 {1} 方式"),
    MISSING_PARAM("MISSING_PARAM", "缺少请求参数 >> {0}"),
    INVALID_PARAM("INVALID_PARAM", "请求参数异常 >> {0}"),
    INVALID_PARAM_TYPE("INVALID_PARAM_TYPE", "请求参数类型错误 >> {0}"),
    BUSINESS_ERROR("BUSINESS_ERROR", "业务异常 >> 原因[{1}]"),
    ;

    CommonExceptionEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    private String code;

    private String msg;

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getMsg() {
        return msg;
    }

}
