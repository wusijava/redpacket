package com.zanclick.redpacket.user.service;

import com.zanclick.redpacket.common.base.service.BaseService;
import com.zanclick.redpacket.user.entity.Menu;

/**
 * @author 27720
 * @date 2020-12-07 17:55:48
 **/
public interface MenuService extends BaseService<Menu,Long> {


    /**
     * 查询开启的菜单
     *
     * @param code
     * @return
     */
    Menu queryOpenByCode(String code);
}
