package com.zanclick.redpacket.user.service.impl;

import com.zanclick.redpacket.common.base.dao.mybatis.BaseMapper;
import com.zanclick.redpacket.common.base.service.impl.BaseMybatisServiceImpl;
import com.zanclick.redpacket.user.entity.HomeMenu;
import com.zanclick.redpacket.user.entity.Menu;
import com.zanclick.redpacket.user.entity.RoleMenu;
import com.zanclick.redpacket.user.entity.UserRole;
import com.zanclick.redpacket.user.mapper.RoleMenuMapper;
import com.zanclick.redpacket.user.query.RoleMenuQuery;
import com.zanclick.redpacket.user.service.HomeMenuService;
import com.zanclick.redpacket.user.service.MenuService;
import com.zanclick.redpacket.user.service.RoleMenuService;
import com.zanclick.redpacket.user.service.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * @author 27720
 * @date 2020-12-07 17:56:25
 **/
@Service
public class RoleMenuServiceImpl extends BaseMybatisServiceImpl<RoleMenu,Long> implements RoleMenuService {

    @Autowired
    private RoleMenuMapper roleMenuMapper;

    @Autowired
    private HomeMenuService homeMenuService;
    @Autowired
    private UserRoleService userRoleService;
    @Autowired
    private MenuService menuService;


    @Override
    protected BaseMapper<RoleMenu, Long> getBaseMapper() {
        return roleMenuMapper;
    }

    @Override
    public List<HomeMenu> queryByUid(String Uid) {
        UserRole userRole = new UserRole();
        userRole.setUserId(Uid);
        List<UserRole> userRoles = userRoleService.queryList(userRole);
        List<RoleMenu> menus=new ArrayList<>();
        for (UserRole ur:userRoles) {
            Integer roleId = ur.getRoleType();
            //根据角色id查询对应的权限
            RoleMenuQuery query = new RoleMenuQuery();
            query.setType(roleId);
            query.setState(RoleMenu.State.open.getCode());
            List<RoleMenu> menu = this.queryList(query);
            for (RoleMenu roleMenu:menu) {
                menus.add(roleMenu);
            }
        }
        SortedMap<String, HomeMenu> homeMenuMap = new TreeMap<>();
        SortedMap<String, List<Menu>> btnMenuMap = new TreeMap<>();
        for (RoleMenu roleMenu : menus) {
            Menu menu = menuService.queryOpenByCode(roleMenu.getMenuCode());
            if (menu == null) {
                continue;
            }
            if (menu.getType().equals(3)){
                List<Menu> menuss = btnMenuMap.get(menu.getHomeCode());
                if (menuss == null){
                    menuss = new ArrayList<>();
                }
                if (!menuss.contains(menu)){
                    menuss.add(menu);
                }
                btnMenuMap.put(menu.getHomeCode(),menuss);
                continue;
            }
            HomeMenu homeMenu = homeMenuMap.get(menu.getHomeCode());
            if (homeMenu == null) {
                homeMenu = homeMenuService.queryOpenByCode(roleMenu.getHomeMenuCode());
                if (homeMenu == null) {
                    continue;
                }
            }
            List<Menu> menuList = homeMenu.getSubmenus();
            if (menuList == null) {
                menuList = new ArrayList<>();
            }
            if (!menuList.contains(menu)){
                menuList.add(menu);
            }
            homeMenu.setSubmenus(menuList);
            homeMenuMap.put(homeMenu.getCode(), homeMenu);
        }
        List<HomeMenu> menuItem = getHomeList(homeMenuMap);
        for (HomeMenu menu:menuItem){
            if (menu.getSubmenus() == null){
                continue;
            }
            for (Menu m:menu.getSubmenus()){
                m.setBtnMenu(btnMenuMap.get(m.getCode()));
            }
        }
        return menuItem;
    }


    /**
     * map转list
     *
     * @param homeMenuMap
     */
    private List<HomeMenu> getHomeList(SortedMap<String, HomeMenu> homeMenuMap) {
        List<HomeMenu> menus = new ArrayList<>();
        for (String menu : homeMenuMap.keySet()) {
            menus.add(homeMenuMap.get(menu));
        }
        return menus;
    }
}
