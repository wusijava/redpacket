package com.zanclick.redpacket.core.vo.h5;

import lombok.Data;

import java.util.Date;

/**
 * @ Description   :  转账记录vo
 * @ Author        :  wusi
 * @ CreateDate    :  2020/12/11$ 10:43$
 */
@Data
public class TransferRecordVo {
    /**
     * 金额
     */
    private String amount;
    /**
     * 创建时间
     */
    private String createTime;
    /**
     * 订单号
     */
    private String tradeNo;
    /**
     * 收款账号
     */
    private String receiveNo;
    /**
     * 描述
     */
    private String settleDesc;
}
