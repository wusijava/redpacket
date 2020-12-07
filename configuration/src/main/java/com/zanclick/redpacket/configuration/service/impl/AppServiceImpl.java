package com.zanclick.redpacket.configuration.service.impl;

import com.zanclick.redpacket.common.base.dao.mybatis.BaseMapper;
import com.zanclick.redpacket.common.base.service.impl.BaseMybatisServiceImpl;
import com.zanclick.redpacket.configuration.entity.App;
import com.zanclick.redpacket.configuration.mapper.AppMapper;
import com.zanclick.redpacket.configuration.service.AppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author admin
 * @date 2020-12-04 17:20:04
 **/
@Service
public class AppServiceImpl extends BaseMybatisServiceImpl<App,Long> implements AppService {

    @Autowired
    private AppMapper appMapper;


    @Override
    protected BaseMapper<App, Long> getBaseMapper() {
        return appMapper;
    }

    @Override
    public App queryByAppId(String appId) {
        return appMapper.queryByAppId(appId);
    }
}
