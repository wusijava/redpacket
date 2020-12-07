package com.zanclick.redpacket.configuration.service;


import com.zanclick.redpacket.common.base.service.BaseService;
import com.zanclick.redpacket.configuration.entity.IpControl;

import java.util.List;

/**
 * @author long5
 * @date 2020-08-21 22:26:58
 **/
public interface IpControlService extends BaseService<IpControl, Long> {

    /**
     * 根据应用ID查询 IP白名单列表
     *
     * @param appId 应用ID
     * @return IP白名单列表
     */
    List<IpControl> queryByAppId(String appId);
}
