package com.zanclick.redpacket.core.entity;

import com.zanclick.redpacket.common.model.Identifiable;
import lombok.Data;

import java.util.Date;

/**
 * 钱包
 *  wusi 2020-12-07
 */
@Data
public class Wallet implements Identifiable<Long> {

  private Long id;
  /**
   * 红包领取账号
   */
  private String userName;
  /**
   * 总金额
   */
  private String totalAmount;
  /**
   * 可提现金额
   */
  private String canWithdrawAmount;
  /**
   * 1开启  0关闭
   */
  private Integer state;
  /**
   * 收款账号
   */
  private String receiveNo;
  /**
   * 收款实名
   */
  private String receiveName;
  /**
   * 创建时间
   */
  private Date createTime;



}
