package com.zanclick.redpacket.user.service;

import com.zanclick.redpacket.common.base.service.BaseService;
import com.zanclick.redpacket.user.entity.HomeMenu;
import com.zanclick.redpacket.user.entity.RoleMenu;

import java.util.List;

/**
 * @author 27720
 * @date 2020-12-07 17:56:25
 **/
public interface RoleMenuService extends BaseService<RoleMenu,Long> {


    /**
     * 查询开启的菜单
     *
     * */
    List<HomeMenu> queryByUid(String Uid);
}
