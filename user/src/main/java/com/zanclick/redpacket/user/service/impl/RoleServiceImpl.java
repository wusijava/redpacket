package com.zanclick.redpacket.user.service.impl;

import com.zanclick.redpacket.common.base.dao.mybatis.BaseMapper;
import com.zanclick.redpacket.common.base.service.impl.BaseMybatisServiceImpl;
import com.zanclick.redpacket.user.entity.Role;
import com.zanclick.redpacket.user.mapper.RoleMapper;
import com.zanclick.redpacket.user.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author admin
 * @date 2020-12-07 14:39:16
 **/
@Service
public class RoleServiceImpl extends BaseMybatisServiceImpl<Role,Long> implements RoleService {

    @Autowired
    private RoleMapper roleMapper;


    @Override
    protected BaseMapper<Role, Long> getBaseMapper() {
        return roleMapper;
    }
}
