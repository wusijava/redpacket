package com.zanclick.redpacket.core.controller;

import com.zanclick.redpacket.common.entity.Response;
import com.zanclick.redpacket.common.utils.DataUtils;
import com.zanclick.redpacket.core.entity.RedPacket;
import com.zanclick.redpacket.core.entity.RedPacketRecord;
import com.zanclick.redpacket.core.query.RedPacketQuery;
import com.zanclick.redpacket.core.query.RedPacketRecordQuery;
import com.zanclick.redpacket.core.service.RedPacketRecordService;
import com.zanclick.redpacket.core.vo.RedPacketRecordVo;
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
 * @Date: 2020/12/8 14:17
 */
@Slf4j
@RestController
@RequestMapping(value = "api/web/redPacketRecord")
public class WebRedPacketRecordController {
    @Autowired
    private RedPacketRecordService redPacketRecordService;

    /**
     * 红包列表
     *
     * @param query
     * @return
     */
    @RequestMapping("list")
    public Response<Page<RedPacketRecordVo>> list(RedPacketRecordQuery query) {
        if (DataUtils.isEmpty(query.getPage())) {
            query.setPage(0);
        }
        if (DataUtils.isEmpty(query.getLimit())) {
            query.setLimit(10);
        }
        try {
            Pageable pageable = PageRequest.of(query.getPage(), query.getLimit());
            Page<RedPacketRecord> page = redPacketRecordService.queryPage(query, pageable);
            List<RedPacketRecordVo> voList = new ArrayList<>();
            for (RedPacketRecord redPacketRecord : page.getContent()) {
                voList.add(getListVo(redPacketRecord));
            }
            Page<RedPacketRecordVo> voPage = new PageImpl<RedPacketRecordVo>(voList, pageable, page.getTotalElements());
            return Response.ok(voPage);
        } catch (Exception e) {
            log.error("获取交易列表异常:{}", e);
            return Response.fail("查询失败");
        }
    }

    private RedPacketRecordVo getListVo(RedPacketRecord redPacketRecord) {
        RedPacketRecordVo vo = new RedPacketRecordVo();
        vo.setAmount(redPacketRecord.getAmount());
        vo.setTradeNo(redPacketRecord.getTradeNo());
        vo.setOutTradeNo(redPacketRecord.getOutTradeNo());
        vo.setTitle(redPacketRecord.getTitle());
        vo.setAppId(redPacketRecord.getAppId());
        vo.setState(redPacketRecord.getState());
        vo.setType(redPacketRecord.getType());
        vo.setMerchantNo(redPacketRecord.getMerchantNo());
        vo.setSellerNo(redPacketRecord.getSellerNo());
        vo.setCreateTime(redPacketRecord.getCreateTime());
        vo.setPayNo(redPacketRecord.getPayNo());
        vo.setReceiveName(redPacketRecord.getReceiveName());
        vo.setReceiveName(redPacketRecord.getReceiveName());
        vo.setReceiveNo(redPacketRecord.getReceiveNo());
        vo.setAliUserId(redPacketRecord.getAliUserId());
        vo.setReason(redPacketRecord.getReason());
        return vo;

    }
}
