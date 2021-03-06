package com.zanclick.redpacket.user.entity;

import com.zanclick.redpacket.common.model.Identifiable;
import lombok.Data;

/**
 * @author duchong
 * @description 权限
 * @date 2019-8-3 10:05:37
 */
@Data
public class Role implements Identifiable<Long> {

    private Long id;

    private Integer type;

    private String name;

    /**
     * 备注
     */
    private String remark;
    /**
     * 冻结 0冻 1未冻
     */
    private  Integer state;

    public enum State {
        open(1, "未冻结"),
        close(0, "已冻结");

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

    public String getStateDesc(){
        if (State.close.getCode().equals(state)){
            return  State.close.getDesc();
        }else {
            return  State.open.getDesc();
        }
    }

}
