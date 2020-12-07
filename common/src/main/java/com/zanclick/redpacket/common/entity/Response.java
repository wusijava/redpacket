package com.zanclick.redpacket.common.entity;
/**
 * Created by lvlu on 2017/12/19.
 */

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(description = "返回结果")
public class Response<T> implements Serializable {

    private String code;

    private String msg;

    private T data;

    private Response() {
    }

    private Response(String code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public static <T> Response<T> ok(T data) {
        return response(CodeMsg.SUCCESS.getCode(), CodeMsg.SUCCESS.getMsg(), data);
    }

    public static <T> Response<T> ok(String msg) {
        return response(CodeMsg.SUCCESS.getCode(), msg, null);
    }

    public static <T> Response<T> ok(String msg, T data) {
        return response(CodeMsg.SUCCESS.getCode(), msg, data);
    }

    public static <T> Response<T> expire(T data) {
        return response(CodeMsg.EXPIRE.getCode(), CodeMsg.EXPIRE.getMsg(), data);
    }

    public static <T> Response<T> expire(String msg) {
        return response(CodeMsg.EXPIRE.getCode(), msg, null);
    }

    public static <T> Response<T> expire(String msg, T data) {
        return response(CodeMsg.EXPIRE.getCode(), msg, data);
    }

    public static <T> Response<T> fail(T data) {
        return response(CodeMsg.FAIL.getCode(), null, data);
    }

    public static <T> Response<T> fail(String msg) {
        return response(CodeMsg.FAIL.getCode(), msg, null);
    }

    public static <T> Response<T> fail(String msg, T data) {
        return response(CodeMsg.FAIL.getCode(), msg, data);
    }

    public static <T> Response<T> response(String code, String msg, T data) {
        Response<T> response = new Response<>(code, msg, data);
        return response;
    }


    public boolean isSuccess() {
        if (CodeMsg.SUCCESS.getCode().equals(this.code)) {
            return true;
        } else {
            return false;
        }
    }

}
