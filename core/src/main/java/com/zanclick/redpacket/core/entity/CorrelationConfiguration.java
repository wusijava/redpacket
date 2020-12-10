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

    /**
     * 0为已删除  1为未删除
     */
    private Integer isDelete;

    public enum Type {
        MERCHANTNO(1,"商户编码"),
        CASHIERPHONE(2,"办单收营员手机号"),
        SELLERNO(3, "收款账号");


        private Integer code;
        private String desc;

        Type(Integer code, String desc) {
            this.code = code;
            this.desc = desc;
        }

        public Integer getCode() {
            return code;
        }

        public void setCode(Integer code) {
            this.code = code;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }
    }
    public String getTypeDesc() {
        if (Type.MERCHANTNO.getCode().equals(type)) {
            return Type.MERCHANTNO.getDesc();
        }
        if (Type.CASHIERPHONE.getCode().equals(type)) {
            return Type.CASHIERPHONE.getDesc();
        }
        if (Type.SELLERNO.getCode().equals(type)) {
            return Type.SELLERNO.getDesc();
        }
        return null;
    }
}
