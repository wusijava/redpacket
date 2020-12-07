package com.zanclick.redpacket.user.modal;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@Data
public class RoleListDTO {

    @ApiModelProperty(value = "ID")
    private Long id;

    @ApiModelProperty(value = "用户姓名")
    private String name;

    @ApiModelProperty(value = "联系电话")
    private String phone;

    @ApiModelProperty(value = "创建时间")
    private String time;

    @ApiModelProperty(value = "所属大区")
    private String areaTypeName;

    @ApiModelProperty(value = "状态 0 开启  1 关闭")
    private Integer state;

    @ApiModelProperty(value = "状态 0 开启  1 关闭")
    private String stateDesc;

    @ApiModelProperty(value = "上级名称")
    private String parentName;
}
