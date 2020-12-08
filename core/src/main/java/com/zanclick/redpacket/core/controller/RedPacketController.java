package com.zanclick.redpacket.core.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zanclick.redpacket.common.entity.Response;
import com.zanclick.redpacket.common.jms.JmsMessaging;
import com.zanclick.redpacket.common.jms.SendMessage;
import com.zanclick.redpacket.common.utils.DataUtils;
import com.zanclick.redpacket.common.utils.LoginContext;
import com.zanclick.redpacket.core.entity.RedPacket;
import com.zanclick.redpacket.core.service.RedPacketService;
import com.zanclick.redpacket.core.vo.RedPacketVo;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

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
    public Response<List<RedPacketVo>> queryRedPacket(String tradeNo, String phoneNo) {
        if (DataUtils.isEmpty(tradeNo) && DataUtils.isEmpty(phoneNo)) {
            return Response.fail("订单号与顾客手机号不能同时为空!");
        }
        RedPacket query = new RedPacket();
        if (!DataUtils.isEmpty(tradeNo) && !DataUtils.isEmpty(phoneNo)) {
            query.setTradeNo(tradeNo);
            query.setCustomPhone(phoneNo);
        }
        if (!DataUtils.isEmpty(tradeNo)) {
            query.setTradeNo(tradeNo);
        }
        if (!DataUtils.isEmpty(phoneNo)) {
            query.setCustomPhone(phoneNo);
        }
        List<RedPacket> redPackets = redPacketService.queryList(query);
        List<RedPacketVo> voList = new ArrayList<>();
        if (DataUtils.isEmpty(redPackets)) {
            return Response.fail("无数据");
        }
        for (RedPacket packet : redPackets) {
            voList.add(getVo(packet));
        }
        return Response.ok(voList);
    }

    private RedPacketVo getVo(RedPacket packet) {
        RedPacketVo VO = new RedPacketVo();
        VO.setId(packet.getId());
        VO.setTradeNo(packet.getTradeNo());
        VO.setAmount(packet.getAmount());
        VO.setState(packet.getState());
        return VO;
    }

    @ApiOperation("领取红包")
    @RequestMapping(value = "getRedPacket")
    @ResponseBody
    public Response queryRedPacket(Long id) {
        if(DataUtils.isEmpty(id)){
            return Response.fail("id为空,未查询到红包信息!");
        }
        RedPacket packet = redPacketService.queryById(id);
        if(DataUtils.isEmpty(packet)){
            return Response.fail("未查询到红包信息!");
        }
        if(RedPacket.State.RECEIVED.getCode().equals(packet.getState())){
            return Response.fail("红包已领取!");
        }
        if(RedPacket.State.SUCCESS.getCode().equals(packet.getState())){
            return Response.fail("红包已到账!");
        }
        if(RedPacket.State.CANCLE.getCode().equals(packet.getState())){
            return Response.fail("红包已取消!");
        }
        LoginContext.RequestUser currentUser = LoginContext.getCurrentUser();
        System.out.println(currentUser);
        if(RedPacket.State.WAITING.getCode().equals(packet.getState())){
            SendMessage.sendMessage(JmsMessaging.RECEIVE_REDPACKET_MESSAGE,String.valueOf(id));
        }

        return null;
    }

}
