package com.zanclick.redpacket.user.controller;


import com.zanclick.redpacket.common.entity.Response;
import com.zanclick.redpacket.common.utils.DataUtils;
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
        user.setDefaultRoleCode(DataRoles.USER.getCode());
        try {
            userService.insert(user);
        } catch (Exception e) {
            log.error("注册失败,{},{}", userName, password);
        }
        return Response.ok("注册成功!");
    }

}
