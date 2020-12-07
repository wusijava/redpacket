package com.zanclick.redpacket.user.service.impl;

import com.zanclick.redpacket.common.base.dao.mybatis.BaseMapper;
import com.zanclick.redpacket.common.base.service.impl.BaseMybatisServiceImpl;
import com.zanclick.redpacket.common.utils.DataUtils;
import com.zanclick.redpacket.user.entity.Role;
import com.zanclick.redpacket.user.mapper.RoleMapper;
import com.zanclick.redpacket.user.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author long5
 * @date 2020-11-28 19:45:15
 **/
@Service
public class RoleServiceImpl extends BaseMybatisServiceImpl<Role,Long> implements RoleService {

    private final RoleMapper roleMapper;

    @Autowired
    public RoleServiceImpl(RoleMapper roleMapper) {
        this.roleMapper = roleMapper;
    }


    @Override
    protected BaseMapper<Role, Long> getBaseMapper() {
        return roleMapper;
    }

    @Override
    public Role queryByPhone(String phone) {
        Role role = new Role();
        role.setPhone(phone);
        List<Role> roleList = this.queryList(role, PageRequest.of(0,1));
        return DataUtils.isNotEmpty(roleList) ? roleList.get(0) : null;
    }
}
