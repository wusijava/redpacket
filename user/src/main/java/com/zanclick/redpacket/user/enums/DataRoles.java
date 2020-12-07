package com.zanclick.redpacket.user.enums;

import java.util.HashMap;
import java.util.Map;

public enum DataRoles {
    ADMIN("100","管理员"),
    SERVICE("200","代理角色"),
    MERCHANT("201","地推角色");

    private String code;

    private String desc;

    DataRoles(String code, String desc) {
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

    static Map<String, String> DATA_ROLE = new HashMap<>();

    static {
        DATA_ROLE.put(ADMIN.getCode(),ADMIN.getDesc());
        DATA_ROLE.put(SERVICE.getCode(),SERVICE.getDesc());
        DATA_ROLE.put(MERCHANT.getCode(),MERCHANT.getDesc());
    }


    public static String getRoleName(String code){
        return DATA_ROLE.get(code);
    }
}
