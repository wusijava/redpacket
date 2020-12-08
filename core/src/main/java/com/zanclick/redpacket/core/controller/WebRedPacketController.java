package com.zanclick.redpacket.core.controller;

import com.zanclick.redpacket.common.entity.Response;
import com.zanclick.redpacket.common.utils.DataUtils;
import com.zanclick.redpacket.common.utils.DateUtils;
import com.zanclick.redpacket.core.entity.RedPacket;
import com.zanclick.redpacket.core.query.RedPacketQuery;
import com.zanclick.redpacket.core.service.RedPacketService;
import com.zanclick.redpacket.core.vo.RedPacketVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: huze
 * @Date: 2020/12/8 10:22
 */

@Slf4j
@RestController
@RequestMapping(value = "/api/web/redPacket")
public class WebRedPacketController {

    @Autowired
    private RedPacketService redPacketService;
    /**
     * 红包列表
     *
     * @param query
     * @return
     */
    @RequestMapping("list")
    public Response<Map<String, Object>> list(RedPacketQuery query) {
        if (DataUtils.isEmpty(query.getPage())) {
            query.setPage(0);
        }
        if (DataUtils.isEmpty(query.getLimit())) {
            query.setLimit(10);
        }
        try {
            Pageable pageable = PageRequest.of(query.getPage(), query.getLimit());
            Page<RedPacket> page = redPacketService.queryPage(query, pageable);
            List<RedPacketVo> voList = new ArrayList<>();
            for (RedPacket redPacket : page.getContent()) {
                voList.add(getListVo(redPacket));
            }
            Page<RedPacketVo> voPage = new PageImpl<RedPacketVo>(voList, pageable, page.getTotalElements());
            Map<String, Object> map = new HashMap<>(2);
            map.put("data", voPage);
            map.put("sumRebateAmount", redPacketService.selectSunAmount(query));
            return Response.ok(map);
        } catch (Exception e) {
            log.error("获取交易列表异常:{}", e);
            return Response.fail("查询失败");
        }
    }
    private RedPacketVo getListVo(RedPacket redPacket) {
        RedPacketVo vo = new RedPacketVo();
        vo.setAmount(redPacket.getAmount());
        vo.setAppId(redPacket.getAppId());
        vo.setTradeNo(redPacket.getTradeNo());
        vo.setOutTradeNo(redPacket.getOutTradeNo());
        vo.setTitle(redPacket.getTitle());
        vo.setState(redPacket.getState());
        vo.setStateDesc(redPacket.getStateDesc());
        vo.setType(redPacket.getType());
        vo.setMerchantNo(redPacket.getMerchantNo());
        vo.setCashierPhoneNo(redPacket.getCashierPhoneNo());
        vo.setSellerNo(redPacket.getSellerNo());
        vo.setCustomPhone(redPacket.getCustomPhone());
        vo.setOrderTime(DateUtils.formatDate(redPacket.getOrderTime(), DateUtils.PATTERN_YYYY_MM_DD_HH_MM_SS));
        vo.setDelayDays(redPacket.getDelayDays());
        vo.setIsNextMonthSettle(redPacket.getIsNextMonthSettle());
        vo.setCreateTime(DateUtils.formatDate(redPacket.getCreateTime(), DateUtils.PATTERN_YYYY_MM_DD_HH_MM_SS));
        vo.setCollectAccountNumber(redPacket.getCollectAccountNumber());
        vo.setAccountNo(redPacket.getAccountNo());
        vo.setProvinceName(redPacket.getProvinceName());
        vo.setCashierNo(redPacket.getCashierNo());
        vo.setTradingTime(DateUtils.formatDate(redPacket.getTradingTime(), DateUtils.PATTERN_YYYY_MM_DD_HH_MM_SS));
        return vo;
    }

}
