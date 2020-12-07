package com.zanclick.redpacket.configuration.mapper;

import com.zanclick.redpacket.common.base.dao.mybatis.BaseMapper;
import com.zanclick.redpacket.configuration.entity.AliPayConfiguration;
import org.mapstruct.Mapper;

/**
 * @author long5
 * @date 2020-06-30 21:06:39
 **/
@Mapper
public interface AliPayConfigurationMapper extends BaseMapper<AliPayConfiguration, Long> {
    /**
     * 通过appid获取配置
     * @param
     * @return
     */
    AliPayConfiguration queryByAppId(String appId);

    /**
     * 查询最后一条默认开启数据
     */
    AliPayConfiguration selectLastOne();
}
