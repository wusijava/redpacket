package com.zanclick.redpacket.user.controller;


import com.zanclick.redpacket.common.entity.Response;
import com.zanclick.redpacket.common.jms.JmsMessaging;
import com.zanclick.redpacket.common.jms.SendMessage;
import com.zanclick.redpacket.common.utils.DataUtils;
import com.zanclick.redpacket.common.utils.LoginContext;
import com.zanclick.redpacket.common.utils.PassWordUtil;
import com.zanclick.redpacket.user.entity.User;
import com.zanclick.redpacket.user.enums.DataRoles;
import com.zanclick.redpacket.user.service.UserService;
import com.zanclick.redpacket.user.util.NumberGeneratorUtils;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * @ Description   :  h5登录controller
 * @ Author        :  wusi
 * @ CreateDate    :  2020/12/7$ 15:53$
 */
@Slf4j
@RestController
@RequestMapping(value = "/h5/", produces = MediaType.APPLICATION_JSON_UTF8_VALUE, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
public class H5LoginController {
    @Autowired
    private UserService userService;

    @ApiOperation(value = "登录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username", value = "登录账号", required = true, dataType = "String", paramType = "form"),
            @ApiImplicitParam(name = "password", value = "登录密码", required = true, dataType = "String", paramType = "form"),
    })
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public Response<String> login(String username, String password) {
        return Response.ok("登录成功");
    }

    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    @ResponseBody
    public Response<String> logout() {
        return Response.ok("退出成功");
    }

    @ApiOperation(value = "h5注册")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username", value = "登录账号", required = true, dataType = "String", paramType = "form"),
            @ApiImplicitParam(name = "password", value = "登录密码", required = true, dataType = "String", paramType = "form"),
    })
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    @ResponseBody
    public Response<String> register(String userName, String password, String passwordConfirm) {
        if (DataUtils.isEmpty(userName) || DataUtils.isEmpty(password) || DataUtils.isEmpty(passwordConfirm)) {
            return Response.fail("注册信息不完整!");
        }
        if (!password.trim().equals(passwordConfirm.trim())) {
            return Response.fail("两次密码不一致,请重新输入!");
        }
        User byUsername = userService.findByUsername(userName);
        if (!DataUtils.isEmpty(byUsername)) {
            return Response.fail("用户名已存在!");
        }
        String salt = PassWordUtil.generateSalt();
        User user = new User();
        user.setCreateTime(new Date());
        user.setUsername(userName);
        user.setUid(NumberGeneratorUtils.generatorUid());
        user.setSalt(salt);
        user.setPassword(PassWordUtil.generatePasswordSha1WithSalt(password, salt));
        user.setState(1);
        user.setPwd(password);
        user.setDefaultRoleCode(DataRoles.USER.getCode());
        try {
            userService.insert(user);
            SendMessage.sendMessage(JmsMessaging.CREATE_WALLET_MESSAGE, userName);
        } catch (Exception e) {
            log.error("注册失败,{},{}", userName, password);
        }
        return Response.ok("注册成功!");
    }

    //修改密码
    @RequestMapping(value = "/api/change", method = RequestMethod.POST)
    @ResponseBody
    public Response<String> change(String oldPassword, String password, String passwordConfirm) {
        if (DataUtils.isEmpty(oldPassword)) {
            return Response.fail("缺少旧密码!");
        }
        if (DataUtils.isEmpty(password)) {
            return Response.fail("缺少新密码!");
        }
        if (DataUtils.isEmpty(passwordConfirm)) {
            return Response.fail("缺少确认新密码!");
        }
        if(!password.trim().equals(passwordConfirm.trim())){
            return Response.fail("新密码两次输入不一致!");
        }
        LoginContext.RequestUser user = LoginContext.getCurrentUser();
        System.out.println(user);
        String result = userService.changePassword(Long.valueOf(user.getId()), user.getSalt(), user.getPassword(), oldPassword, password);
        if (result != null) {
            return Response.fail(result);
        }
        return Response.ok("修改成功，请重新登录");
    }
}
