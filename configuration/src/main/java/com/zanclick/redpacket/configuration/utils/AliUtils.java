package com.zanclick.redpacket.configuration.utils;

import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.zanclick.redpacket.configuration.entity.AliPayConfiguration;

public class AliUtils {

    /**
     * 获取支付宝请求工具
     *
     * @param configuration 配置信息
     * @return
     */
    public static AlipayClient getClient(AliPayConfiguration configuration) {
        return new DefaultAlipayClient(configuration.getGateWay(), configuration.getAppId(), configuration.getPrivateKey(), configuration.getFormat(), configuration.getCharset(), configuration.getPublicKey(), configuration.getSignType());
    }
}
