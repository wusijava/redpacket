package com.zanclick.redpacket.core.query;


import com.zanclick.redpacket.core.entity.TransferRecord;
import lombok.Data;


/**
 * 转账记录
 * wusi 2020-12-07
 */
@Data
public class TransferRecordQuery extends TransferRecord {

  private Integer page;

  private Integer limit;

  private String startTime;

  private String endTime;

  private String offset;
}
