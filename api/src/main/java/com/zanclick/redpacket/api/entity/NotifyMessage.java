package com.zanclick.redpacket.api.entity;

import com.zanclick.redpacket.common.model.Identifiable;
import lombok.Data;

import java.util.Date;

/**
 * @author duchong
 * @description 异步消息（首次推送成功不会进这里）
 * @date 2019-11-26 09:50:23
 */
@Data
public class NotifyMessage implements Identifiable<Long> {

    private static final long serialVersionUID = -4284643205140088943L;
    private Long id;
    /**
     * 推送内容
     */
    private String content;

    /**
     * 下次推送时间
     */
    private Date nextSendTime;

    /**
     * 推送地址
     */
    private String url;

    /**
     * 推送状态 0 待推送 1 已推送 -1推送失败
     */
    private Integer state;
    /**
     * 当前推送次数
     */
    private Integer times;

    public enum State {
        FAIL(-1, "推送失败"),
        WAIT(0, "等待推送"),
        SUCCESS(1, "推送成功");
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
