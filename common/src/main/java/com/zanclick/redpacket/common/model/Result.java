package com.zanclick.redpacket.common.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author duchong
 * @description
 * @date 2019-11-23 14:14:07
 */
@Data
@NoArgsConstructor
@ApiModel(description = "后台接口返回数据", value = "Result")
public class Result<T>{

    @ApiModelProperty(value = "具体业务接口返回数据")
    private T data;

    @ApiModelProperty(value = "操作返回码[\"10500\", \"系统异常\"],[\"40015\", \"登陆过期\"],[\"30015\", \"版本过期\"],[\"40500\", \"操作失败\"],[\"20000\", \"操作成功\"]")
    private String code;

    @ApiModelProperty(value = "错误描述")
    private String msg;

    public static <T> Result<T> ok(T data) {
        Result<T> model = new Result<T>();
        model.setCode(CodeMsg.SUCCESS.getCode());
        model.setMsg(CodeMsg.SUCCESS.getMsg());
        model.setData(data);
        return model;
    }

    public static Result fail(String error_msg) {
        Result<String> model = new Result<String>();
        model.setCode(CodeMsg.FAIL.getCode());
        model.setMsg(error_msg);
        return model;
    }

    public static <T> Result fail(String error_msg, T data) {
        Result<T> model = new Result<>();
        model.setCode(CodeMsg.FAIL.getCode());
        model.setMsg(error_msg);
        model.setData(data);
        return model;
    }

    public static Result expired(String error_msg) {
        Result<String> model = new Result<String>();
        model.setCode(CodeMsg.INVALID_LOGIN.getCode());
        model.setMsg(error_msg);
        return model;
    }

    public static Result<String> error(String error_msg) {
        Result<String> model = new Result<String>();
        model.setCode(CodeMsg.SYSTEM_ERROR.getCode());
        model.setMsg(error_msg);
        return model;
    }

    public boolean isSuccess(){
        return CodeMsg.SUCCESS.getCode().endsWith(this.code);
    }

}
