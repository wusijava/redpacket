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



}
