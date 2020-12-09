package com.zanclick.redpacket.user.service;

import com.zanclick.redpacket.common.base.service.BaseService;
import com.zanclick.redpacket.user.entity.User;

/**
 * @author admin
 * @date 2020-12-09 11:05:38
 **/
public interface UserService extends BaseService<User,Long> {

    User findByUsername(String username);

    String changePassword(Long userId, String salt, String password, String oldPassword, String newPassword);
}
