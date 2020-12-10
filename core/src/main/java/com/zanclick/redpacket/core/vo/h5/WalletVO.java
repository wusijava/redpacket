package com.zanclick.redpacket.core.vo.h5;

import lombok.Data;

/**
 * @ Description   :  h5红包vo
 * @ Author        :  wusi
 * @ CreateDate    :  2020/12/9$ 11:57$
 */
@Data
public class WalletVO {
    /**
     * 总金额
     */
    private String totalAmount;
    /**
     * 可提现金额
     */
    private String canWithdrawAmount;
    /**
     * 收款账号
     */
    private String receiveNo;
}
