package com.zanclick.redpacket.configuration.service.impl;


import com.zanclick.redpacket.common.base.dao.mybatis.BaseMapper;
import com.zanclick.redpacket.common.base.service.impl.BaseMybatisServiceImpl;
import com.zanclick.redpacket.configuration.entity.IpControl;
import com.zanclick.redpacket.configuration.mapper.IpControlMapper;
import com.zanclick.redpacket.configuration.service.IpControlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author long5
 * @date 2020-08-21 22:26:58
 **/
@Service
public class IpControlServiceImpl extends BaseMybatisServiceImpl<IpControl,Long> implements IpControlService {

    private final IpControlMapper ipControlMapper;

    @Autowired
    public IpControlServiceImpl(IpControlMapper ipControlMapper) {
        this.ipControlMapper = ipControlMapper;
    }

    @Override
    protected BaseMapper<IpControl, Long> getBaseMapper() {
        return ipControlMapper;
    }

    @Override
    public List<IpControl> queryByAppId(String appId) {
        IpControl control = new IpControl();
        control.setAppId(appId);
        return this.queryList(control);
    }
}
