package com.zanclick.redpacket.user.vo;

import lombok.Data;

import java.util.List;

/**
 * @author duchong
 * @description 主菜单
 * @date 2019-11-13 14:31:08
 */
@Data
public class MenuList {

    private String title;

    private String icon;

    private String path;

    private String name;

    private String component;

    private Integer type;

    private List<String> pageBtns;

    public boolean equals(MenuList list){
        if (list == null || list.getPath() == null){
            return false;
        }
        return list.getPath().equals(this.path);
    }
}
