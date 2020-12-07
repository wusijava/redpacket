package com.zanclick.redpacket.configuration.service;

import com.zanclick.redpacket.common.base.service.BaseService;
import com.zanclick.redpacket.configuration.entity.App;

/**
 * @author admin
 * @date 2020-12-04 17:20:04
 **/
public interface AppService extends BaseService<App,Long> {
    App queryByAppId(String appId);
}
