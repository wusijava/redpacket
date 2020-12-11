package com.zanclick.redpacket.core.entity;


import com.zanclick.redpacket.common.model.Identifiable;
import lombok.Data;

import java.util.Date;

/**
 * 转账记录
 * wusi 2020-12-07
 */
@Data
public class TransferRecord implements Identifiable<Long> {

  private Long id;
  /**
   * 用户名
   */
  private String userName;
  /**
   * 金额
   */
  private String amount;
  /**
   * 创建时间
   */
  private Date createTime;
  /**
   * 订单号
   */
  private String tradeNo;
  /**
   * 收款账号
   */
  private String receiveNo;
  /**
   * 1转入  2提现
   */
  private String type;
  /**
   * type为1 领取时的状态 1到账 2 已领未到账 3 已退单
   */

  private  Integer state;
  /**
   * 红包编号
   */
  private  String packetNo;
  public enum State {
    SUCCESSS(1, "已到账"),
    NOT(2, "未到账"),
    CANCEL(3, "已取消");

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
    if (State.SUCCESSS.getCode().equals(state)){
      return State.SUCCESSS.getDesc();
    }  else if (State.NOT.getCode().equals(state)){
      return State.NOT.getDesc();
    }else if(State.CANCEL.getCode().equals(state)){
      return State.CANCEL.getDesc();
    }
    return null;
  }
}
