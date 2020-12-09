package com.zanclick.redpacket.core.utils;

import com.alipay.api.AlipayClient;
import com.alipay.api.response.*;
import com.zanclick.redpacket.core.payDTO.Transfer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 预授权支付工具类
 *
 * @author duchong
 * @date 2019-7-9 10:44:42
 */
public class AuthorizePayUtil {

    private static Logger _log = LoggerFactory.getLogger(AuthorizePayUtil.class);

    /**
     * 单笔转账
     *
     * @param client
     * @param transfer
     * @return
     */
    public static AlipayFundTransToaccountTransferResponse transfer(AlipayClient client, Transfer transfer){
        return AuthorizeClientUtil.transfer(client,null,transfer.toString());
    }

}
