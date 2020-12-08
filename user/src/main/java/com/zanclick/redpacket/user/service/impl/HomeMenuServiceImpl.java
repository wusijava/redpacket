package com.zanclick.redpacket.user.service.impl;

import com.zanclick.redpacket.common.base.dao.mybatis.BaseMapper;
import com.zanclick.redpacket.common.base.service.impl.BaseMybatisServiceImpl;
import com.zanclick.redpacket.user.entity.HomeMenu;
import com.zanclick.redpacket.user.entity.Menu;
import com.zanclick.redpacket.user.mapper.HomeMenuMapper;
import com.zanclick.redpacket.user.query.HomeMenuQuery;
import com.zanclick.redpacket.user.service.HomeMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 27720
 * @date 2020-12-07 17:55:22
 **/
@Service
public class HomeMenuServiceImpl extends BaseMybatisServiceImpl<HomeMenu,Long> implements HomeMenuService {

    @Autowired
    private HomeMenuMapper homeMenuMapper;


    @Override
    protected BaseMapper<HomeMenu, Long> getBaseMapper() {
        return homeMenuMapper;
    }

    @Override
    public HomeMenu queryOpenByCode(String code) {
        HomeMenuQuery query = new HomeMenuQuery();
        query.setCode(code);
        query.setState(Menu.State.open.getCode());
        List<HomeMenu> menuList = this.queryList(query);
        return menuList == null || menuList.size() == 0 ? null : menuList.get(0);
    }
}
