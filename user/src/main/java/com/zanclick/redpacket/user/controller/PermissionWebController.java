package com.zanclick.redpacket.user.controller;

import com.zanclick.redpacket.common.base.controller.BaseController;
import com.zanclick.redpacket.common.entity.Response;
import com.zanclick.redpacket.common.utils.*;
import com.zanclick.redpacket.user.entity.HomeMenu;
import com.zanclick.redpacket.user.entity.Menu;
import com.zanclick.redpacket.user.entity.User;
import com.zanclick.redpacket.user.query.MenuQuery;
import com.zanclick.redpacket.user.query.UserQuery;
import com.zanclick.redpacket.user.service.HomeMenuService;
import com.zanclick.redpacket.user.service.MenuService;
import com.zanclick.redpacket.user.service.RoleService;
import com.zanclick.redpacket.user.service.UserService;
import com.zanclick.redpacket.user.vo.HomeMenuList;
import com.zanclick.redpacket.user.vo.UserVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.crypto.interfaces.PBEKey;
import java.util.ArrayList;
import java.util.List;

/**
 * 权限管理
 *
 * @author duchong
 * @date 2019-8-3 10:50:06
 */
@Api(description = "权限web接口")
@RestController
@RequestMapping(value = "/api/web/permission")
public class PermissionWebController extends BaseController {



    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;
    @ApiOperation(value = "权限列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "authorization", value = "加密参数", required = true, dataType = "String", paramType = "header"),
    })
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ResponseBody
    public Response<List<HomeMenuList>> list() {
        LoginContext.RequestUser user = LoginContext.getCurrentUser();
        List<HomeMenuList> list = roleService.findPermissionByUid(user.getUid());
        if (DataUtils.isEmpty(list)){
            return Response.fail("没有权限");
        }
        return Response.ok(list);
    }


    /**
     * 账户列表
     * @param query
     * @return
     */
    @ApiOperation(value = "账户列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "authorization", value = "加密参数", required = true, dataType = "String", paramType = "header"),
    })
    @RequestMapping(value = "userList",method = RequestMethod.POST)
    public Response<Page<UserVo>> userList(UserQuery query) {
        if (DataUtils.isEmpty(query.getPage())) {
            query.setPage(0);
        }
        if (DataUtils.isEmpty(query.getLimit())) {
            query.setLimit(10);
        }
        try {
            Pageable pageable = PageRequest.of(query.getPage(), query.getLimit());
            Page<User> page = userService.queryPage(query, pageable);
            List<UserVo> voList = new ArrayList<>();
            for (User user : page.getContent()) {
                voList.add(getListVo(user));
            }
            Page<UserVo> voPage = new PageImpl<UserVo>(voList, pageable, page.getTotalElements());
            return Response.ok(voPage);
        } catch (Exception e) {
            log.error("获取交易列表异常:{}", e);
            return Response.fail("查询失败");
        }
    }

    public UserVo getListVo(User user){
        UserVo vo = new UserVo();
        vo.setUserName(user.getUsername());
        vo.setPassWord(user.getPassword());
        vo.setCreateTime(DateUtils.formatDate(user.getCreateTime(),DateUtils.PATTERN_YYYY_MM_DD_HH_MM_SS));
        vo.setState(user.getState());
        vo.setPwd(user.getPwd());
        return vo;
    }

    @ApiOperation(value = "修改登录密码")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "oldPassword", value = "旧密码", required = true, dataType = "Integer", paramType = "form"),
            @ApiImplicitParam(name = "newPassword", value = "新密码", required = true, dataType = "Integer", paramType = "form"),

            @ApiImplicitParam(name = "authorization", value = "加密参数", required = true, dataType = "String", paramType = "header"),
    })
    @RequestMapping(value = "/changePassword", method = RequestMethod.POST)
    @ResponseBody
    public Response changePassword(UserQuery query) {
        if (DataUtils.isEmpty(query.getOldPassword())) {
            return Response.fail("缺少原密码");
        }
        if (DataUtils.isEmpty(query.getNewPassword())) {
            return Response.fail("缺少新密码");
        }
        if (DataUtils.isEmpty(query.getUsername())) {
            return Response.fail("缺少用户名");
        }
        User user = userService.findByUsername(query.getUsername());
        if(user.getPwd().equals(query.getOldPassword())){
            user.setPwd(query.getNewPassword());
            String password = PassWordUtil.generatePasswordSha1WithSalt(query.getNewPassword(), user.getSalt());
            user.setPassword(password);
            userService.updateById(user);
        }else{
            return Response.fail("原密码错误");
        }
        return Response.ok("修改成功，请重新登录");
    }

    @ResponseBody
    @RequestMapping(value = "changeUserState",method = RequestMethod.POST)
    public Response changeUserState(UserQuery query){
        if(DataUtils.isEmpty(query.getState())){
            return Response.fail("状态修改异常");
        }
        if(DataUtils.isEmpty(query.getUsername())){
            return Response.fail("用户名为空");
        }
        User user = userService.findByUsername(query.getUsername());
        user.setState(query.getState());
        userService.updateById(user);
        return Response.ok("修改成功");
    }

}