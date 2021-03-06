package com.zanclick.redpacket.core.entity;

import com.zanclick.redpacket.common.model.Identifiable;
import lombok.Data;

import java.util.Date;

/**
 * wusi
 * 红包实体 wusi 2020-12-07
 */
@Data
public class RedPacket implements Identifiable<Long> {

  private Long id;
  /**
   * 红包金额
   */
  private String amount;
  /**
   * 订单号
   */
  private String tradeNo;
  /***
   * 外部订单号
   */
  private String outTradeNo;
  /**
   * 借款订单号
   */
  private String brwOrdNo;
  /**
   * 红包标题
   */
  private String title;
  /**
   * 应用id
   */
  private String appId;
  /**
   * 状态 1已创建 2已领取 3已到账 4已取消
   */
  private Integer state;
  /**
   * 类型 1支付宝 2乐薪 3 网商
   */
  private Integer type;
  /**
   * 商户号
   */
  private String merchantNo;
  /**
   * 收银员手机号
   */
  private String cashierPhoneNo;
  /**
   * 收款账号
   */
  private String sellerNo;
  /**
   * 顾客手机号
   */
  private String customPhone;
  /**
   * 创建时间
   */
  private Date createTime;


  private Date orderTime;
  /**
   * 延期到账天数
   */
  private Integer delayDays;
  /**
   * 是否隔月到账 1是  0否
   */
  private Integer isNextMonthSettle;
  /**
   * 红包领取账号
   */
  private String collectAccountNumber;

  /**
   * 到账账号
   */
  private String accountNo;

  /**
   * 省份
   */
  private String provinceName;

  /**
   * 下单营业员编号
   */
  private String cashierNo;


  /**
   * 省份
   */
  private String provinceCode;
  /**
   * 红包编号
   */
  private String packetNo;
  /**
   * 交易时间
   */
  private Date tradingTime;
  public enum State {
    FAIL(-1,"打款失败"),
    WAITING(1,"等待打款"),
    RECEIVED(2, "打款中"),
    SUCCESS(3, "打款成功"),
    CANCLE(4, "用户退单");

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
  public String getStateDesc() {
    if (State.WAITING.getCode().equals(state)) {
      return "等待打款";
    } else if (State.RECEIVED.getCode().equals(state)) {
      return "打款中";
    } else if (State.SUCCESS.getCode().equals(state)) {
      return "打款成功";
    } else if (State.CANCLE.getCode().equals(state)) {
      return "用户退单";
    } else if (State.FAIL.getCode().equals(state)) {
      return "打款失败";
    }
    return null;
  }

  public enum Type {
    ZFB(1,"支付宝"),
    LX(2, "乐薪"),
    WS(3, "网商");

    private Integer code;
    private String desc;

    Type(Integer code, String desc) {
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
  public String getTypeDesc() {
    if (Type.ZFB.getCode().equals(type)) {
      return Type.ZFB.getDesc();
    }
    if (Type.LX.getCode().equals(type)) {
      return Type.LX.getDesc();
    }
    if (Type.WS.getCode().equals(type)) {
      return Type.WS.getDesc();
    }
    return null;
  }

  public Boolean isFailed() {
    return State.FAIL.getCode().equals(state);
  }

  public Boolean isWait() {
    return State.WAITING.getCode().equals(state);
  }

  public Boolean isSuccess() {
    return State.SUCCESS.getCode().equals(state);
  }
  public Boolean isWaiting() {
    return State.RECEIVED.getCode().equals(state);
  }

  public Boolean isRefund() {
    return State.CANCLE.getCode().equals(state);
  }
}
