package com.zanclick.redpacket.configuration.service;

import com.alipay.api.AlipayClient;
import com.zanclick.redpacket.common.base.service.BaseService;
import com.zanclick.redpacket.configuration.entity.AliPayConfiguration;


/**
 * @author long5
 * @date 2020-06-30 21:06:39
 **/
public interface AliPayConfigurationService extends BaseService<AliPayConfiguration, Long> {


    /**
     * 通过直付通获取支付宝配置
     *
     * @param
     * @return
     */
    AlipayClient getClient(Long configurationId);



    /**
     * 通过appid获取配置
     * @param appId
     * @return
     */
    AliPayConfiguration queryByAppId(String appId);

    /**
     * 查询最后一条默认开启数据
     */
    AliPayConfiguration selectLastOne();



}
