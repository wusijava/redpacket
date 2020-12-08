package com.zanclick.redpacket.user.service.impl;

import com.zanclick.redpacket.common.base.dao.mybatis.BaseMapper;
import com.zanclick.redpacket.common.base.service.impl.BaseMybatisServiceImpl;
import com.zanclick.redpacket.common.utils.DataUtils;
import com.zanclick.redpacket.user.entity.HomeMenu;
import com.zanclick.redpacket.user.entity.Menu;
import com.zanclick.redpacket.user.entity.Role;
import com.zanclick.redpacket.user.mapper.RoleMapper;
import com.zanclick.redpacket.user.service.RoleMenuService;
import com.zanclick.redpacket.user.service.RoleService;
import com.zanclick.redpacket.user.vo.HomeMenuList;
import com.zanclick.redpacket.user.vo.MenuList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 27720
 * @date 2020-12-07 17:56:10
 **/
@Service
public class RoleServiceImpl extends BaseMybatisServiceImpl<Role,Long> implements RoleService {

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private RoleMenuService roleMenuService;


    @Override
    protected BaseMapper<Role, Long> getBaseMapper() {
        return roleMapper;
    }

    @Override
    public List<HomeMenuList> findPermissionByUid(String Uid) {
        List<HomeMenu> homeMenuList = roleMenuService.queryByUid(Uid);
        return  getList(homeMenuList);
    }

    private List<HomeMenuList> getList(List<HomeMenu> homeMenuList){
        List<HomeMenuList> menuList = new ArrayList<>();
        for (HomeMenu menu:homeMenuList){
            HomeMenuList list = new HomeMenuList();
            list.setTitle(menu.getTitle());
            list.setIcon(menu.getIcon());
            list.setName(menu.getName());
            if (DataUtils.isNotEmpty(menu.getSubmenus())){
                List<MenuList> submenus = new ArrayList<>();
                for (Menu m:menu.getSubmenus()){
                    MenuList ml = new MenuList();
                    ml.setTitle(m.getTitle());
                    ml.setIcon(m.getIcon());
                    ml.setName(m.getName());
                    ml.setPath(m.getPath());
                    ml.setComponent(m.getComponent());
                    ml.setType(m.getType());
                    if (DataUtils.isNotEmpty(m.getBtnMenu())){
                        List<String> pageBtns=new ArrayList<>();
                        for (Menu mu: m.getBtnMenu()) {
                            pageBtns.add(mu.getPath());
                        }
                        ml.setPageBtns(pageBtns);
                    }
                    submenus.add(ml);
                }
                list.setSubmenus(submenus);
            }
            menuList.add(list);
        }
        return menuList;
    }
}
