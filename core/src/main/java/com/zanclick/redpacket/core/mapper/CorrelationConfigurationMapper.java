package com.zanclick.redpacket.core.mapper;

import com.zanclick.redpacket.common.base.dao.mybatis.BaseMapper;
import com.zanclick.redpacket.core.entity.CorrelationConfiguration;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author wusi
 * @date 2020-12-09 10:10:34
 **/
@Mapper
public interface CorrelationConfigurationMapper extends BaseMapper<CorrelationConfiguration,Long> {


}
