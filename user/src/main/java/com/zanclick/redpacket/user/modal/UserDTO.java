package com.zanclick.redpacket.user.modal;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@Data
public class UserDTO {

    @ApiModelProperty(value = "UID")
    private String uid;

    @ApiModelProperty(value = "TOKEN")
    private String token;

    @ApiModelProperty(value = "昵称")
    private String nickname;

    @ApiModelProperty(value = "联系电话")
    private String phone;

    @ApiModelProperty(value = "登录账号")
    private String username;
}
