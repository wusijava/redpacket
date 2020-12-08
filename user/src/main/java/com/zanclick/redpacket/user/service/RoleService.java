package com.zanclick.redpacket.user.service;

import com.zanclick.redpacket.common.base.service.BaseService;
import com.zanclick.redpacket.user.entity.Role;
import com.zanclick.redpacket.user.vo.HomeMenuList;

import java.util.List;

/**
 * @author 27720
 * @date 2020-12-07 17:56:10
 **/
public interface RoleService extends BaseService<Role,Long> {


    /**
     * 查询该角色的权限
     *
     * @param
     */
    List<HomeMenuList> findPermissionByUid(String Uid);

}
