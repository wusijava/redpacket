package com.zanclick.redpacket.api.notify;


import com.alibaba.fastjson.JSONObject;
import com.zanclick.redpacket.api.entity.NotifyMessage;
import com.zanclick.redpacket.api.service.NotifyMessageService;
import com.zanclick.redpacket.common.utils.DataUtils;
import com.zanclick.redpacket.common.utils.DateUtils;
import com.zanclick.redpacket.configuration.NotifyConfiguration;
import com.zanclick.redpacket.configuration.entity.App;
import com.zanclick.redpacket.configuration.service.AppService;
import com.zanclick.redpacket.sdk.ApiRequestParam;
import com.zanclick.redpacket.sdk.enums.ApiMethod;
import com.zanclick.redpacket.sdk.util.ApiUtils;
import com.zanclick.redpacket.sdk.util.HttpUtils;
import com.zanclick.redpacket.sdk.util.SignUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author duchong
 * @description 异步消息推送（轮询重复推送待实现）
 * @date 2020-8-25 14:54:16
 */
@Slf4j
@Service
public class AsyncNotifySender {

    final AppService appService;

    final NotifyConfiguration configuration;

    final NotifyMessageService notifyMessageService;

    @Autowired
    public AsyncNotifySender(AppService appService, NotifyConfiguration configuration, NotifyMessageService notifyMessageService) {
        this.appService = appService;
        this.configuration = configuration;
        this.notifyMessageService = notifyMessageService;
    }

    /**
     * 异步消息推送
     *
     * @param url     推送地址
     * @param appId   应用ID
     * @param content 推送内容
     * @param method  推送方法
     */
    @Async("notifyExecutor")
    public void sendMessage(ApiMethod method, String content, String url, String appId) {
        ApiRequestParam param = new ApiRequestParam();
        param.setApp_id(appId);
        param.setCharset(configuration.getCharset());
        param.setMethod(method.getMethod());
        param.setContent(content);
        param.setRequest_id(ApiUtils.getRequestId());
        param.setSign_type(configuration.getSignType());
        param.setTimestamp(ApiUtils.getTimestamp());
        param.setVersion(method.getVersion());
        App app = appService.queryByAppId(appId);
        if (app == null) {
            log.error("推送信息异常:{},{},{},{}", method.getMethod(), content, url, appId);
            insertMessage(param, url, NotifyMessage.State.FAIL.getCode(), null);
            return;
        }
        try {
            SignUtils.sign(param, app.getPrivateKey());
        } catch (Exception e) {
            log.error("签名异常:{},{},{},{}", method.getMethod(), content, url, appId);
        }
        String s = HttpUtils.post(param, url);
        if (s == null || !s.equalsIgnoreCase("SUCCESS")) {
            insertMessage(param, url, NotifyMessage.State.WAIT.getCode(), 0);
        }
    }
    /**
     * 轮询重复推送异步消息
     */
    @Async("notifyExecutor")
    protected void repeatSend() {
        AsyncNotifyTime async = new AsyncNotifyTime(configuration.getMillisecond());
        while (true) {
            async.now();
            List<NotifyMessage> messageList = notifyMessageService.queryWaitSendMessage();
            for (NotifyMessage message : messageList) {
                send(message);
            }
            sleep(async.sleepTime());
        }
    }

    /**
     * 睡眠方法
     * @param times 睡眠时间，毫秒
     */
    protected void sleep(Long times) {
        if (times <= 0){
            return;
        }
        try {
            Thread.sleep(times);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 推送异步消息
     *
     * @param message 参数
     */
    protected void send(NotifyMessage message) {
        ApiRequestParam param = JSONObject.parseObject(message.getContent(), ApiRequestParam.class);
        String s = HttpUtils.post(param, message.getUrl());
        if (s == null || !s.equalsIgnoreCase("SUCCESS")) {
            if (message.getTimes() != null && message.getTimes() >= configuration.getTimes().length - 1) {
                log.error("通知失败:{}", JSONObject.toJSONString(message));
                notifyMessageService.sendFailed(message.getId());
                return;
            }
            if (DataUtils.isEmpty(message.getTimes())) {
                message.setTimes(0);
            }
            Integer times = message.getTimes() + 1;
            message.setTimes(times);
            if (DataUtils.isNotEmpty(times)) {
                Date nextSendTime = DateUtils.addMinute(new Date(), configuration.getTimes()[times]);
                message.setNextSendTime(nextSendTime);
            }
            notifyMessageService.change(message);
            return;
        }
        notifyMessageService.sendSuccess(message.getId());
    }


    /**
     * 存放异步消息
     *
     * @param param 参数
     * @param url   推送链接
     * @param state 推送状态
     * @param times 推送次数
     */
    protected void insertMessage(ApiRequestParam param, String url, Integer state, Integer times) {
        NotifyMessage message = new NotifyMessage();
        message.setState(state);
        message.setContent(JSONObject.toJSONString(param));
        message.setUrl(url);
        message.setTimes(times);
        if (DataUtils.isNotEmpty(times)) {
            Date nextSendTime = DateUtils.addMinute(new Date(), configuration.getTimes()[times]);
            message.setNextSendTime(nextSendTime);
        }
        notifyMessageService.insert(message);
    }

}
