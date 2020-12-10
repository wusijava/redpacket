package com.zanclick.redpacket.core.service;

import com.zanclick.redpacket.common.base.service.BaseService;
import com.zanclick.redpacket.core.entity.CorrelationConfiguration;

/**
 * @author 27720
 * @date 2020-12-09 14:46:12
 **/
public interface CorrelationConfigurationService extends BaseService<CorrelationConfiguration,Long> {
    CorrelationConfiguration findByUsername(String username);
}
