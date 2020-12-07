package com.zanclick.redpacket.user.service;

import com.zanclick.redpacket.common.base.service.BaseService;
import com.zanclick.redpacket.user.entity.Role;

/**
 * @author long5
 * @date 2020-11-28 19:45:15
 **/
public interface RoleService extends BaseService<Role,Long> {


    Role queryByPhone(String phone);
}
