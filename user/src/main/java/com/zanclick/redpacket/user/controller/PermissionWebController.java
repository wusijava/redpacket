package com.zanclick.redpacket.user.controller;

import com.zanclick.redpacket.common.base.controller.BaseController;
import com.zanclick.redpacket.common.entity.Response;
import com.zanclick.redpacket.common.utils.DataUtils;
import com.zanclick.redpacket.common.utils.LoginContext;
import com.zanclick.redpacket.user.entity.HomeMenu;
import com.zanclick.redpacket.user.entity.Menu;
import com.zanclick.redpacket.user.query.MenuQuery;
import com.zanclick.redpacket.user.service.HomeMenuService;
import com.zanclick.redpacket.user.service.MenuService;
import com.zanclick.redpacket.user.service.RoleService;
import com.zanclick.redpacket.user.vo.HomeMenuList;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

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


}