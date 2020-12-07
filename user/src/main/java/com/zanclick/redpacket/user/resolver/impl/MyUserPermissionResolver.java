package com.zanclick.redpacket.user.resolver.impl;

import com.zanclick.redpacket.common.utils.PassWordUtil;
import com.zanclick.redpacket.user.entity.User;
import com.zanclick.redpacket.user.modal.LoginUser;
import com.zanclick.redpacket.user.resolver.UserPermissionResolver;
import com.zanclick.redpacket.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author tuchuan
 * @description
 * @date 2019/3/15 10:51
 */
@Slf4j
@Component(value = "webUserPermissionResolver")
public class MyUserPermissionResolver extends UserPermissionResolver {

    private final UserService userService;

    @Autowired
    public MyUserPermissionResolver(UserService userService) {
        this.userService = userService;
    }


    @Override
    public LoginUser getLoginUser(String username) {
        User user = userService.findByUsername(username);
        if (user == null){
           throw  new UserPermissionResolver.UsernameAndPasswordException("账号密码错误");
        }
        if (user.getDefaultRoleCode() == null){
            throw  new UserPermissionResolver.UsernameAndPasswordException("权限配置错误");
        }
        if (!User.State.OPEN.getCode().equals(user.getState())){
            throw  new UserPermissionResolver.UsernameAndPasswordException("账号状态异常");
        }
        if (User.State.CLOSE.getCode().equals(user.getState())){
            throw  new UserPermissionResolver.UsernameAndPasswordException("账号已被禁用");
        }
//        Role role = roleService.queryByRelationRoleCode(user.getDefaultRoleCode());
//        if (role == null){
//            throw  new UserPermissionResolver.UsernameAndPasswordException("权限配置异常");
//        }
        LoginUser lu = new LoginUser();
        lu.setUsername(user.getUsername());
        lu.setPassword(user.getPassword());
        lu.setPhone(user.getMobile());
        lu.setSalt(user.getSalt());
        lu.setNickname(user.getNickname());
        lu.setUid(user.getUid());
        return lu;
    }

    @Override
    public Boolean comparePassWord(String password, String intPutPwd, String salt) {
        String loginPass = PassWordUtil.generatePasswordSha1WithSalt(intPutPwd, salt);
        return loginPass.equals(password);
    }

}
