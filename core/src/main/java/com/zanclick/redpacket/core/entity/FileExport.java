package com.zanclick.redpacket.core.entity;

import com.zanclick.redpacket.common.model.Identifiable;
import lombok.Data;

import java.util.Date;

/**
 * @Author: huze
 * @Date: 2020/12/8 16:22
 */
@Data
public class FileExport implements Identifiable<Long> {

    private Long id;

    /**
     * 文件名称
     */
    private String fileName;

    /**
     * 导出时间
     */
    private Date exprotTime;

    /**
     * 状态1-导出失败  2 正在导出 3导出成功 4已失效
     */
    private Integer state;

    /**
     导出人
     */
    private String user;

    //0 zip  1excel
    private Integer type;

    private Date finishTime;

    public enum State {
        fail(1, "导出失败"),
        waiting(2, "导出中"),
        success(3, "导出成功"),
        expiry(4, "已失效");

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
        if (State.success.getCode().equals(state)){
            return State.success.getDesc();
        }  else if (State.waiting.getCode().equals(state)){
            return State.waiting.getDesc();
        }else if(State.expiry.getCode().equals(state)){
            return State.expiry.getDesc();
        }else{
            return State.fail.getDesc();
        }
    }

}
