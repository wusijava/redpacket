package com.zanclick.redpacket.user.vo;

import com.zanclick.redpacket.user.entity.Menu;
import lombok.Data;

import java.util.List;

/**
 * @Author: huze
 * @Date: 2020/12/7 17:33
 */
@Data
public class HomeMenuList {

    private String title;

    private String name;

    private String icon;

    private List<MenuList> submenus;
}