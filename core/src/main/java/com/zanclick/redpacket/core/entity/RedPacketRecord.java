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



}
