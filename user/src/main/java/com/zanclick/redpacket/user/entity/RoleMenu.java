package com.zanclick.redpacket.user.entity;

import com.zanclick.redpacket.common.model.Identifiable;
import lombok.Data;

/**
 * @author duchong
 * @description 权限
 * @date 2019-8-3 10:05:37
 */
@Data
public class RoleMenu implements Identifiable<Long> {

    private Long id;

    private Integer type;

    private String homeMenuCode;

    private String menuCode;

    private Integer state;

    public enum State {
        open(1, "开启"),
        close(0, "冻结");

        private Integer code;
        private String desc;

        State(Integer code, String desc) {
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

}
