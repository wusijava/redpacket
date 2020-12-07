package com.zanclick.redpacket.user.modal;

import lombok.Data;

import java.io.Serializable;

@Data
public class LoginInfo implements Serializable {

    private String uid;

    private String jwt;

    private String type;

    private String username;

    public enum Type {
        WEB("WEB", "后台登录"),
        H5("H5", "H5登录"),
        OPEN("OPEN", "开放接口");

        private String code;

        private String desc;

        Type(String code, String desc) {
            this.code = code;
            this.desc = desc;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }
    }

    public String loginKey(){
        return this.getUid() + "-" + this.getUsername() +"-"+ this.getType();
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof LoginInfo)) {
            return false;
        }
        LoginInfo info = (LoginInfo) object;
        if (info == null || info.getUid() == null || info.getUsername() == null || info.getType() == null) {
            return false;
        }
        return this.getUid().equals(info.getUid()) && this.getType().equals(info.getType()) && this.getUsername().equals(info.getUsername());
    }
}
