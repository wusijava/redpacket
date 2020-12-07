package com.zanclick.redpacket.user.modal;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@Data
public class RoleQueryDTO {

    @ApiModelProperty(value = "用户姓名")
    private String name;

    @ApiModelProperty(value = "联系电话")
    private String phone;

    @ApiModelProperty(value = "创建时间")
    private String startTime;

    @ApiModelProperty(value = "结束时间")
    private String endTime;

    @ApiModelProperty(value = "所属大区")
    private String areaType;

    @ApiModelProperty(value = "角色类型")
    private String dataRoleCode;
}
