package com.zanclick.redpacket.core.vo;

import lombok.Data;

/**
 * @ Description   :  红包详情VO
 * @ Author        :  wusi
 * @ CreateDate    :  2020/12/8$ 10:17$
 */
@Data
public class RedPacketVO {
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
     * 状态 1已创建 2已领取 3已到账 4已取消
     */
    private Integer state;

}
