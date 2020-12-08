package com.zanclick.redpacket.user.service.impl;

import com.zanclick.redpacket.common.base.dao.mybatis.BaseMapper;
import com.zanclick.redpacket.common.base.service.impl.BaseMybatisServiceImpl;
import com.zanclick.redpacket.user.entity.Menu;
import com.zanclick.redpacket.user.mapper.MenuMapper;
import com.zanclick.redpacket.user.query.MenuQuery;
import com.zanclick.redpacket.user.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 27720
 * @date 2020-12-07 17:55:48
 **/
@Service
public class MenuServiceImpl extends BaseMybatisServiceImpl<Menu,Long> implements MenuService {

    @Autowired
    private MenuMapper menuMapper;


    @Override
    protected BaseMapper<Menu, Long> getBaseMapper() {
        return menuMapper;
    }

    @Override
    public Menu queryOpenByCode(String code) {
        MenuQuery query = new MenuQuery();
        query.setCode(code);
        query.setState(Menu.State.open.getCode());
        List<Menu> menuList = this.queryList(query);
        return menuList == null || menuList.size() == 0 ? null : menuList.get(0);
    }
}
