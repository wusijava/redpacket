package com.zanclick.redpacket.user.modal;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@Data
public class RegisterDTO {

    @ApiModelProperty(value = "登录账号",required = true)
    private String username;

    @ApiModelProperty(value = "TOKEN",required = true)
    private String smsCode;

    @ApiModelProperty(value = "代理名称",required = true)
    private String name;

    @ApiModelProperty(value = "角色类型",required = true)
    private String dataRoleCode;

    @ApiModelProperty(value = "所属大区",required = true)
    private String areaType;
}
