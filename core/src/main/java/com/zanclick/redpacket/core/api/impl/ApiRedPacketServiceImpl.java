package com.zanclick.redpacket.core.api.impl;

import com.zanclick.redpacket.api.anonation.OpenApiService;
import com.zanclick.redpacket.api.util.ApiRequestUtil;
import com.zanclick.redpacket.common.utils.DataUtils;
import com.zanclick.redpacket.common.utils.StringUtils;
import com.zanclick.redpacket.configuration.entity.AliPayConfiguration;
import com.zanclick.redpacket.configuration.entity.App;
import com.zanclick.redpacket.configuration.service.AliPayConfigurationService;
import com.zanclick.redpacket.configuration.service.AppService;
import com.zanclick.redpacket.core.api.ApiRedPacketService;
import com.zanclick.redpacket.core.entity.RedPacket;
import com.zanclick.redpacket.core.service.RedPacketService;
import com.zanclick.redpacket.sdk.enums.ApiErrorEnum;
import com.zanclick.redpacket.sdk.exception.BusinessException;
import com.zanclick.redpacket.sdk.model.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @Author: huze
 * @Date: 2020/12/10 16:04
 */
@Slf4j
@Service
@OpenApiService
public class ApiRedPacketServiceImpl implements ApiRedPacketService {
    @Autowired
    private AliPayConfigurationService aliPayConfigurationService;
    @Autowired
    private  AppService appService;
    @Autowired
    private RedPacketService redPacketService;
    @Override
    public EstablishRedPacketResultModel createRedPacket(EstablishRedPacketModel establishRedPacketModel) {
        EstablishRedPacketResultModel model = new EstablishRedPacketResultModel();
        check(ApiRequestUtil.getAppId());
        RedPacket redPacket = new RedPacket();
        redPacket.setAppId(ApiRequestUtil.getAppId());
        redPacket.setOutTradeNo(establishRedPacketModel.getOutTradeNo());
        redPacket.setAmount(establishRedPacketModel.getAmount());
        redPacket.setBrwOrdNo(establishRedPacketModel.getBrwOrdNo());
        redPacket.setTitle(establishRedPacketModel.getTitle());
        redPacket.setType(establishRedPacketModel.getType());
        redPacket.setMerchantNo(establishRedPacketModel.getMerchantNo());
        redPacket.setCashierPhoneNo(establishRedPacketModel.getCashierPhoneNo());
        redPacket.setSellerNo(establishRedPacketModel.getSellerNo());
        redPacket.setCustomPhone(establishRedPacketModel.getCustomPhone());
        redPacket.setDelayDays(establishRedPacketModel.getDelayDays());
        redPacket.setIsNextMonthSettle(establishRedPacketModel.getIsNextMonthSettle());
        redPacket.setCollectAccountNumber(establishRedPacketModel.getCollectAccountNumber());
        redPacket.setProvinceCode(establishRedPacketModel.getProvinceCode());
        redPacket.setProvinceName(establishRedPacketModel.getProvinceName());
        redPacket.setCashierNo(establishRedPacketModel.getCashierNo());
        String packetNo = StringUtils.getTradeNo();
        redPacket.setPacketNo(packetNo);
        redPacket.setCreateTime(new Date());
        redPacket.setState(RedPacket.State.WAITING.getCode());
        redPacketService.insert(redPacket);
        model.setOutTradeNo(establishRedPacketModel.getOutTradeNo());
        model.setPacketNo(packetNo);
        model.setReason("创建成功");
        return model;
    }

    @Override
    public QueryRedPacketResultModel queryRedPacket(QueryRedPacketModel queryRedPacketModel) {
        QueryRedPacketResultModel model = new QueryRedPacketResultModel();
        check(ApiRequestUtil.getAppId());
        RedPacket redPacket = redPacketService.findByPacketNo(queryRedPacketModel.getPacketNo());
        if(DataUtils.isEmpty(redPacket)){
            throw new BusinessException(ApiErrorEnum.BUSINESS_ERROR, "红包不存在");
        }
        model.setAmount(redPacket.getAmount());
        model.setOutTradeNo(redPacket.getOutTradeNo());
        model.setPacketNo(redPacket.getPacketNo());
        model.setStateDesc(redPacket.getStateDesc());
        return model;
    }

    @Override
    public InvalidRedPacketResultModel invalidRedPacket(InvalidRedPacketModel invalidRedPacketModel) {
        check(ApiRequestUtil.getAppId());
        InvalidRedPacketResultModel model = new InvalidRedPacketResultModel();
        RedPacket redPacket = redPacketService.findByPacketNo(invalidRedPacketModel.getPacketNo());
        if(DataUtils.isEmpty(redPacket)){
            throw new BusinessException(ApiErrorEnum.BUSINESS_ERROR, "红包不存在");
        }
        redPacket.setState(RedPacket.State.CANCLE.getCode());
        model.setResult("取消红包成功");
        model.setOutTradeNo(redPacket.getOutTradeNo());
        model.setPacketNo(redPacket.getPacketNo());
        return model;
    }

    public void check(String appId){
        try {
            AliPayConfiguration aliPayConfiguration = aliPayConfigurationService.selectLastOne();
            if(DataUtils.isNotEmpty(aliPayConfiguration) && aliPayConfiguration.getState().equals(0)){
                throw new BusinessException(ApiErrorEnum.BUSINESS_ERROR, "通道配置关闭");
            }
            if (DataUtils.isEmpty(aliPayConfiguration)) {
                throw new BusinessException(ApiErrorEnum.BUSINESS_ERROR, "通道配置错误");
            }
            App app = appService.queryByAppId(appId);
            if(DataUtils.isNotEmpty(app) && app.getState().equals(0)){
                throw new BusinessException(ApiErrorEnum.BUSINESS_ERROR, "业务系统未接入");
            }
        } catch (BusinessException e) {
            e.printStackTrace();
        }
    }
}
