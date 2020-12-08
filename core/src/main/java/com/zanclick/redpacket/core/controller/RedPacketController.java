package com.zanclick.redpacket.core.controller;

import com.zanclick.redpacket.common.entity.Response;
import com.zanclick.redpacket.common.utils.DataUtils;
import com.zanclick.redpacket.core.entity.RedPacket;
import com.zanclick.redpacket.core.query.RedPacketQuery;
import com.zanclick.redpacket.core.service.RedPacketService;
import com.zanclick.redpacket.core.vo.RedPacketVo;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ Description   :  红包controller
 * @ Author        :  wusi
 * @ CreateDate    :  2020/12/7$ 18:11$
 */
@Slf4j
@RestController
@RequestMapping(value = "/h5/")
public class RedPacketController {
    @Autowired
    private RedPacketService redPacketService;

    @ApiOperation("查询红包")
    @RequestMapping(value = "queryRedPacket")
    @ResponseBody
    public Response<Boolean> queryRedPacket(String tradeNo, String phoneNo) {
        if (DataUtils.isEmpty(tradeNo) && DataUtils.isEmpty(phoneNo)) {
            return Response.fail("订单号与顾客手机号不能同时为空!");
        }
        RedPacket query=new RedPacket();
        if(!DataUtils.isEmpty(tradeNo)&&!DataUtils.isEmpty(phoneNo)){
            query.setTradeNo(tradeNo);
            query.setCustomPhone(phoneNo);
        }
        if(!DataUtils.isEmpty(tradeNo)){
            query.setTradeNo(tradeNo);
        }
        if(!DataUtils.isEmpty(phoneNo)){
            query.setTradeNo(phoneNo);
        }
        Long num = redPacketService.queryCount(query);
        if(num == 0){
            return Response.ok(false);
        }
        return Response.ok(true);
    }

}
