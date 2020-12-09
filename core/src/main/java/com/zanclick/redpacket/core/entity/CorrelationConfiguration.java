package com.zanclick.redpacket.core.entity;

import com.zanclick.redpacket.common.model.Identifiable;
import lombok.Data;

import java.util.Date;

/**
 * @ Description   :  关联对象配置
 * @ Author        :  wusi
 * @ CreateDate    :  2020/12/9$ 10:05$
 */
@Data
public class CorrelationConfiguration implements Identifiable<Long> {
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
    private Date createTime;
}
