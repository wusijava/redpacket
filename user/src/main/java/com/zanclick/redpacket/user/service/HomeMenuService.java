package com.zanclick.redpacket.user.service;

import com.zanclick.redpacket.common.base.service.BaseService;
import com.zanclick.redpacket.user.entity.HomeMenu;

/**
 * @author 27720
 * @date 2020-12-07 17:55:22
 **/
public interface HomeMenuService extends BaseService<HomeMenu,Long> {
    /**
     * 查询开启的菜单
     *
     * @param code
     * @return
     */
    HomeMenu queryOpenByCode(String code);
}
