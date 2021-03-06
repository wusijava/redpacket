package com.zanclick.redpacket.core.entity;

import com.zanclick.redpacket.common.model.Identifiable;
import lombok.Data;

import java.util.Date;

/**
 * 红包领取记录
 * wusi 2020-12-07
 */
@Data
public class RedPacketRecord implements Identifiable<Long> {

  private Long id;
  /**
   * 红包金额
   */
  private String amount;
  /**
   * 订单号
   */
  private String tradeNo;
  /**
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
   * 商户收款账号
   */
  private String sellerNo;
  /**
   * 创建时间
   */
  private Date createTime;
  /**
   * 付款账号
   */
  private String payNo;
  /**
   * 收款人姓名
   */
  private String receiveName;
  /**
   * 收款人账号
   */
  private String receiveNo;
  /**
   * 收款人uid
   */
  private String aliUserId;
  /**
   * 领取失败原因
   */
  private String reason;

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
   * 交易时间
   */
  private Date tradingTime;

  private String customPhone;
  private String packetNo;
  /**
   * 到账时间
   */
  private Date arrivalTime;
  /**
   * 领取的登录账号
   */
  private String userName;
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
    if (RedPacket.State.WAITING.getCode().equals(state)) {
      return "等待打款";
    } else if (RedPacket.State.RECEIVED.getCode().equals(state)) {
      return "打款中";
    } else if (RedPacket.State.SUCCESS.getCode().equals(state)) {
      return "打款成功";
    } else if (RedPacket.State.CANCLE.getCode().equals(state)) {
      return "用户退单";
    } else if (RedPacket.State.FAIL.getCode().equals(state)) {
      return "打款失败";
    }
    return null;
  }

}
