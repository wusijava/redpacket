package com.zanclick.redpacket.user.vo;

import lombok.Data;

/**
 * @Author: huze
 * @Date: 2020/12/9 10:47
 */
@Data
public class UserVo {
    private String userName;
    private String passWord;
    private String createTime;
    private Integer state;
    private String pwd;
}
