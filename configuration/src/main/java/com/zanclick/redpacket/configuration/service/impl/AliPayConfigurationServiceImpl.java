package com.zanclick.redpacket.configuration.service.impl;


import com.alipay.api.AlipayClient;
import com.zanclick.redpacket.common.base.dao.mybatis.BaseMapper;
import com.zanclick.redpacket.common.base.service.impl.BaseMybatisServiceImpl;
import com.zanclick.redpacket.common.utils.DataUtils;
import com.zanclick.redpacket.configuration.entity.AliPayConfiguration;
import com.zanclick.redpacket.configuration.mapper.AliPayConfigurationMapper;
import com.zanclick.redpacket.configuration.service.AliPayConfigurationService;
import com.zanclick.redpacket.configuration.utils.AliUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author long5
 * @date 2020-06-30 21:06:39
 **/
@Service
public class AliPayConfigurationServiceImpl extends BaseMybatisServiceImpl<AliPayConfiguration, Long> implements AliPayConfigurationService {

    private final AliPayConfigurationMapper aliPayConfigurationMapper;

    @Autowired
    public AliPayConfigurationServiceImpl(AliPayConfigurationMapper authorizeConfigurationMapper) {
        this.aliPayConfigurationMapper = authorizeConfigurationMapper;
    }

    @Override
    protected BaseMapper<AliPayConfiguration, Long> getBaseMapper() {
        return aliPayConfigurationMapper;
    }

    @Override
    public AlipayClient getClient(Long configurationId) {
        AliPayConfiguration aliPayConfiguration = aliPayConfigurationMapper.selectById(configurationId);
        if (DataUtils.isEmpty(aliPayConfiguration)){
            return null;
        }
        return AliUtils.getClient(aliPayConfiguration);
    }

    @Override
    public AliPayConfiguration queryByAppId(String appId) {
        return aliPayConfigurationMapper.queryByAppId(appId);
    }

    @Override
    public AliPayConfiguration selectLastOne() {
        return aliPayConfigurationMapper.selectLastOne();
    }
}
