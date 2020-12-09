package com.zanclick.redpacket.user.entity;

import com.zanclick.redpacket.common.enums.BaseEnum;
import com.zanclick.redpacket.common.model.Identifiable;
import io.swagger.models.auth.In;
import lombok.Data;

import java.util.Date;

/**
 * @author duchong
 * @description 用户
 * @date 2019-8-3 10:05:37
 */
@Data
public class User implements Identifiable<Long> {

    private Long id;

    private String uid;

    private String username;

    private String password;

    private String mobile;

    private String nickname;

    private Date createTime;

    private Integer state;

    private String defaultRoleCode;

    private String salt;

    private Integer type;

    private String roleTypes;
    /**
     * 明文密码
     */
    private String pwd;

    public enum Type {
        /**
         * 管理员
         */
        ADMIN(0);
        private Integer code;

        Type(Integer code) {
            this.code = code;
        }

        public Integer getCode() {
            return code;
        }

        public void setCode(Integer code) {
            this.code = code;
        }
    }

    public String getTypeDesc(){
        if (User.Type.ADMIN.getCode().equals(type)){
            return "管理员";
        }
        return  null;
    }

    public enum State implements BaseEnum<Integer> {
        /**
         * 开启
         */
        OPEN(1),
        /**
         * 注销
         */
        CANCEL(-1),
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
        } else if (State.CANCEL.getCode().equals(state)) {
            return "已注销";
        }
        return null;
    }
}
