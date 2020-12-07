package com.zanclick.redpacket.configuration.mapper;

import com.zanclick.redpacket.common.base.dao.mybatis.BaseMapper;
import com.zanclick.redpacket.configuration.entity.App;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author admin
 * @date 2020-12-04 17:20:04
 **/
@Mapper
public interface AppMapper extends BaseMapper<App,Long> {

    App queryByAppId(String appId);

}
