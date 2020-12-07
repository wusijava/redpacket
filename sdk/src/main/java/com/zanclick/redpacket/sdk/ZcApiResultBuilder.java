package com.zanclick.redpacket.sdk;


import com.zanclick.redpacket.sdk.enums.ApiCodeMsg;

/**
 * @author duchong
 * @description 对外接口 返回结果创建
 * @date 2020-5-28 11:56:42
 */
public class ZcApiResultBuilder {

    public static ZcApiResult error(String error_code, String error_msg) {
        ZcApiResult model = new ZcApiResult() {
            @Override
            public Class modelClass() {
                return null;
            }
        };
        model.setError_msg(error_msg);
        model.setError_code(error_code);
        model.setCode(ApiCodeMsg.SYSTEM_ERROR.getCode());
        model.setMsg(ApiCodeMsg.SYSTEM_ERROR.getMsg());
        return model;
    }

    public static ZcApiResult fail(String error_code, String error_msg){
        ZcApiResult model = new ZcApiResult() {
            @Override
            public Class modelClass() {
                return null;
            }
        };
        model.setError_msg(error_msg);
        model.setError_code(error_code);
        model.setCode(ApiCodeMsg.FAIL.getCode());
        model.setMsg(ApiCodeMsg.FAIL.getMsg());
        return model;
    }

    public static ZcApiResult success(){
        ZcApiResult model = new ZcApiResult() {
            @Override
            public Class modelClass() {
                return null;
            }
        };
        model.setCode(ApiCodeMsg.SUCCESS.getCode());
        model.setMsg(ApiCodeMsg.SUCCESS.getMsg());
        return model;
    }

    private ZcApiResultBuilder() {
    }
}
