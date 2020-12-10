package com.zanclick.redpacket.core.mapper;

import com.zanclick.redpacket.common.base.dao.mybatis.BaseMapper;
import com.zanclick.redpacket.core.entity.CorrelationConfiguration;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author 27720
 * @date 2020-12-09 14:46:12
 **/
@Mapper
public interface CorrelationConfigurationMapper extends BaseMapper<CorrelationConfiguration,Long> {

    CorrelationConfiguration findByUsername(String username);
}
