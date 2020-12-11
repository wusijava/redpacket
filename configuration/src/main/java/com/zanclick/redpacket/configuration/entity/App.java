package com.zanclick.redpacket.configuration.entity;

import com.zanclick.redpacket.common.model.Identifiable;
import lombok.Data;

import java.util.Date;

/**
 * @author duchong
 * @description 应用信息
 * @date 2019-11-26 09:50:23
 */
@Data
public class App implements Identifiable<Long> {

    private Long id;

    /**
     * 应用ID
     */
    private String appId;

    /**
     * 应用状态 0 关闭的 1开启的
     */
    private Integer state;

    /**
     * 应用名称
     */
    private String name;

    /**
     * 应用私钥
     */
    private String privateKey;

    /**
     * 应用公钥
     */
    private String publicKey;

    /**
     * 合作方的公钥
     */
    private String appPublicKey;

    /**
     * 这个一般是空的
     */
    private String appPrivateKey;


    /**
     * 安全校验
     */
    private Integer checkSign;

    /**
     * ip控制
     */
    private Integer ipControl;

    /**
     * 推送红包创建结果地址
     */
    private String pushCreateResultUrl;

    /**
     * 推送红包取消通知地址
     */
    private String pushCancleResultUrl;

    /**
     * 红包领取结果通知
     */
    private String pushGetResultUrl;
    /**
     * //校验时间
     */
    private Date createTime;

    public enum State {
        CLOSED(0, "关闭"),
        OPEN(1, "开启");
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

    public enum CheckSign {
        CLOSED(0, "关闭"),
        OPEN(1, "开启");
        private Integer code;
        private String desc;

        CheckSign(Integer code, String desc) {
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

    public enum IpControl {
        CLOSED(0, "关闭"),
        OPEN(1, "开启");
        private Integer code;
        private String desc;

        IpControl(Integer code, String desc) {
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
