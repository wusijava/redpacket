package com.zanclick.redpacket.core.controller;

import com.zanclick.redpacket.common.entity.Response;
import com.zanclick.redpacket.common.utils.DataUtils;
import com.zanclick.redpacket.common.utils.DateUtils;
import com.zanclick.redpacket.core.entity.CorrelationConfiguration;
import com.zanclick.redpacket.core.query.CorrelationConfigurationQuery;
import com.zanclick.redpacket.core.query.RedPacketQuery;
import com.zanclick.redpacket.core.service.CorrelationConfigurationService;
import com.zanclick.redpacket.core.vo.CorrelationConfigurationVo;
import com.zanclick.redpacket.user.entity.User;
import com.zanclick.redpacket.user.query.UserQuery;
import com.zanclick.redpacket.user.vo.UserVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.xmlbeans.impl.xb.xsdschema.Public;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.PublicKey;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Author: huze
 * @Date: 2020/12/9 14:53
 */
@Slf4j
@RestController
@RequestMapping(value = "/api/web/CorrelationConfiguration")
public class CorrelationConfigurationController {
    @Autowired
    private CorrelationConfigurationService correlationConfigurationService;

    @RequestMapping("list")
    public Response<Page<CorrelationConfigurationVo>> list(CorrelationConfigurationQuery query) {
        if (DataUtils.isEmpty(query.getPage())) {
            query.setPage(0);
        }
        if (DataUtils.isEmpty(query.getLimit())) {
            query.setLimit(10);
        }
        try {
            Pageable pageable = PageRequest.of(query.getPage(), query.getLimit());
            Page<CorrelationConfiguration> page = correlationConfigurationService.queryPage(query, pageable);
            List<CorrelationConfigurationVo> voList = new ArrayList<>();
            for (CorrelationConfiguration correlationConfiguration : page.getContent()) {
                voList.add(getListVo(correlationConfiguration));
            }
            Page<CorrelationConfigurationVo> voPage = new PageImpl<CorrelationConfigurationVo>(voList, pageable, page.getTotalElements());
            return Response.ok(voPage);
        } catch (Exception e) {
            log.error("获取交易列表异常:{}", e);
            return Response.fail("查询失败");
        }
    }

    private CorrelationConfigurationVo getListVo(CorrelationConfiguration correlationConfiguration){
        CorrelationConfigurationVo vo  = new CorrelationConfigurationVo();
        vo.setCorrelationName(correlationConfiguration.getCorrelationName());
        vo.setCreateTime(DateUtils.formatDate(correlationConfiguration.getCreateTime(),DateUtils.PATTERN_YYYY_MM_DD_HH_MM_SS));
        vo.setIsDelete(correlationConfiguration.getIsDelete());
        vo.setState(correlationConfiguration.getState());
        vo.setTypeDesc(correlationConfiguration.getTypeDesc());
        vo.setType(correlationConfiguration.getType());
        vo.setUserName(correlationConfiguration.getUserName());
        return vo;
    }

    @ResponseBody
    @RequestMapping(value = "changeRedPackNoState",method = RequestMethod.POST)
    public Response changeRedPackNoState(CorrelationConfigurationQuery query){
        if(DataUtils.isEmpty(query.getState())){
            return Response.fail("状态修改异常");
        }
        if(DataUtils.isEmpty(query.getUserName())){
            return Response.fail("用户名为空");
        }
        CorrelationConfiguration correlationConfiguration = correlationConfigurationService.findByUsername(query.getUserName());
        correlationConfiguration.setState(query.getState());
        correlationConfigurationService.updateById(correlationConfiguration);
        return Response.ok("修改成功");
    }

    @ResponseBody
    @RequestMapping(value = "deleteAccount",method = RequestMethod.POST)
    public Response deleteAccount(CorrelationConfigurationQuery query){
        if(DataUtils.isEmpty(query.getUserName())){
            return Response.fail("用户名为空");
        }
        CorrelationConfiguration correlationConfiguration = correlationConfigurationService.findByUsername(query.getUserName());
        correlationConfiguration.setIsDelete(0);
        correlationConfigurationService.updateById(correlationConfiguration);
        return Response.ok("删除成功");
    }

    @ResponseBody
    @RequestMapping(value = "addClaimAccount",method = RequestMethod.POST)
    public Response addClaimAccount(CorrelationConfigurationQuery query){
        if(DataUtils.isEmpty(query.getUserName())){
            return Response.fail("用户名为空");
        }
        if(DataUtils.isEmpty(query.getType())){
            return Response.fail("类型为空");
        }
        if(DataUtils.isEmpty(query.getUserName())){
            return Response.fail("批量领取关联对象为空");
        }
        CorrelationConfiguration correlationConfiguration = new CorrelationConfiguration();
        correlationConfiguration.setIsDelete(1);
        correlationConfiguration.setState(1);
        correlationConfiguration.setCorrelationName(query.getCorrelationName());
        correlationConfiguration.setUserName(query.getUserName());
        correlationConfiguration.setCreateTime(new Date());
        correlationConfiguration.setType(query.getType());
        correlationConfigurationService.insert(correlationConfiguration);
        return Response.ok("添加成功");
    }
}
