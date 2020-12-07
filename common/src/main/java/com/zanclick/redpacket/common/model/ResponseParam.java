package com.zanclick.redpacket.common.model;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;

import java.io.Serializable;

/**
 * 请求结果返回
 *
 * @author duchong
 * @date 2019-7-5 10:25:29
 */
@Data
public class ResponseParam<T> extends Param implements Serializable {

    private String code;

    private String message;

    private T data;

    static String SUCCESS = "20000";

    static String FAIL = "10500";

    public void setSuccess(){
        this.code = SUCCESS;
    }

    public boolean isSuccess(){
        return SUCCESS.equals(code);
    }

    public void setFail(){
        this.code = FAIL;
    }

    @Override
    public String toString() {
        return JSONObject.toJSONString(this);
    }
}
