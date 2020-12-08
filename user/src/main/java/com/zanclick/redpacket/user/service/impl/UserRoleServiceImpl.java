package com.zanclick.redpacket.user.service.impl;

import com.zanclick.redpacket.common.base.dao.mybatis.BaseMapper;
import com.zanclick.redpacket.common.base.service.impl.BaseMybatisServiceImpl;
import com.zanclick.redpacket.user.entity.UserRole;
import com.zanclick.redpacket.user.mapper.UserRoleMapper;
import com.zanclick.redpacket.user.service.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author 27720
 * @date 2020-12-08 10:58:08
 **/
@Service
public class UserRoleServiceImpl extends BaseMybatisServiceImpl<UserRole,Long> implements UserRoleService {

    @Autowired
    private UserRoleMapper userRoleMapper;


    @Override
    protected BaseMapper<UserRole, Long> getBaseMapper() {
        return userRoleMapper;
    }
}
