package com.zanclick.redpacket.user.modal;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@Data
public class RoleEditDTO {

    @ApiModelProperty(value = "登录账号",required = true)
    private Long id;

    @ApiModelProperty(value = "登录账号",required = true)
    private String username;

    @ApiModelProperty(value = "代理名称",required = true)
    private String name;

    @ApiModelProperty(value = "上级代理",required = false)
    private String parentCode;

    @ApiModelProperty(value = "所属大区",required = false)
    private String areaType;
}
