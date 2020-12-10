package com.zanclick.redpacket.core.vo;

import lombok.Data;

import java.util.Date;

/**
 * @Author: huze
 * @Date: 2020/12/9 14:55
 */
@Data
public class CorrelationConfigurationVo {

    private Long id;
    /**
     * 登录用户名
     */
    private String userName;
    /**
     * 关联对象名称
     */
    private String correlationName;
    /**
     * 类型  1商户号 2办单收银员手机号 3收款账号
     */
    private Integer type;
    /**
     * 状态 1开启 0关闭
     */
    private Integer state;
    /**
     * 创建时间
     */
    private String createTime;

    /**
     * 0为已删除  1为未删除
     */
    private Integer isDelete;

    private String typeDesc;
}
