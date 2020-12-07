package com.zanclick.redpacket.user.entity;


import com.zanclick.redpacket.common.enums.BaseEnum;
import com.zanclick.redpacket.common.model.Identifiable;
import lombok.Data;

import java.util.Date;

/**
 * @author duchong
 * @description 角色信息
 * @date 2019-8-3 10:05:37
 */
@Data
public class Role implements Identifiable<Long> {

    private Long id;

    /**
     * 用户编码
     */
    private String uid;

    private String name;

    private String phone;

    /**
     * 数据权限编码
     */
    private String dataRoleCode;

    /**
     * 角色编码
     */
    private String code;

    private String parentCode;

    /**
     * 自定义角色名称
     */
    private String roleName;

    private Integer state;

    private Date createTime;

    /**
     *所属大区
     * */
    private String areaType;

    public enum State implements BaseEnum<Integer> {
        /**
         * 开启
         */
        OPEN(1),
        /**
         * 关闭
         */
        CLOSE(0);

        private Integer code;

        State(Integer code) {
            this.code = code;
        }

        @Override
        public Integer getCode() {
            return code;
        }

        public void setCode(Integer code) {
            this.code = code;
        }
    }

    public String getStateDesc() {
        if (State.OPEN.getCode().equals(state)) {
            return "开启的";
        } else if (State.CLOSE.getCode().equals(state)) {
            return "关闭的";
        }
        return null;
    }

}
