package com.zanclick.redpacket.api.notify;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

/**
 * @author duchong
 * @description 异步消息推送（轮询重复推送待实现）
 * @date 2020-8-25 14:54:16
 */
@Slf4j
@Service
public class AsyncNotifyInIt {

   /* private final AsyncNotifySender messageSender;

    @Autowired
    public AsyncNotifyInIt(AsyncNotifySender messageSender) {
        this.messageSender = messageSender;
    }

    @PostConstruct
    public void init(){
        messageSender.repeatSend();
    }*/

}
