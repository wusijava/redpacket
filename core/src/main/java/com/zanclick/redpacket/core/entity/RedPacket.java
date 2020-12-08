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


  private String orderTime;

  private Integer delayDays;

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
   * 交易时间
   */
  private Date tradingTime;
  public enum State {
    WAITING(1,"待领取"),
    RECEIVED(2, "已领取"),
    SUCCESS(3, "已到账"),
    CANCLE(4, "已取消");

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
      return "已创建";
    } else if (State.RECEIVED.getCode().equals(state)) {
      return "已领取";
    } else if (State.SUCCESS.getCode().equals(state)) {
      return "已到账";
    } else if (State.CANCLE.getCode().equals(state)) {
      return "已取消";
    }
    return null;
  }

}
