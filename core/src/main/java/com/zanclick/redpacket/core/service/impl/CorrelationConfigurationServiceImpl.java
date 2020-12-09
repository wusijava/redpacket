package com.zanclick.redpacket.core.service.impl;

import com.zanclick.redpacket.common.base.dao.mybatis.BaseMapper;
import com.zanclick.redpacket.common.base.service.impl.BaseMybatisServiceImpl;
import com.zanclick.redpacket.core.entity.CorrelationConfiguration;
import com.zanclick.redpacket.core.mapper.CorrelationConfigurationMapper;
import com.zanclick.redpacket.core.service.CorrelationConfigurationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author wusi
 * @date 2020-12-09 10:10:34
 **/
@Service
public class CorrelationConfigurationServiceImpl extends BaseMybatisServiceImpl<CorrelationConfiguration,Long> implements CorrelationConfigurationService {

    @Autowired
    private CorrelationConfigurationMapper correlationConfigurationMapper;


    @Override
    protected BaseMapper<CorrelationConfiguration, Long> getBaseMapper() {
        return correlationConfigurationMapper;
    }
}
