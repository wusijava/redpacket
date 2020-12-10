package com.zanclick.redpacket.user.controller;


import com.zanclick.redpacket.api.context.RequestContext;
import com.zanclick.redpacket.common.entity.Response;
import com.zanclick.redpacket.common.utils.DataUtils;
import com.zanclick.redpacket.common.utils.DateUtils;
import com.zanclick.redpacket.common.utils.LoginContext;
import com.zanclick.redpacket.user.entity.User;
import com.zanclick.redpacket.user.query.UserQuery;
import com.zanclick.redpacket.user.service.UserService;
import com.zanclick.redpacket.user.vo.UserVo;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RequestMapping(value = "/web/user")
@RestController
@Component
public class UserWebController {


    @ApiOperation(value = "登录")
    @RequestMapping(value = "/login")
    @ResponseBody
    public Response login(String username, String password) {
        return Response.ok("登录成功");
    }


}
