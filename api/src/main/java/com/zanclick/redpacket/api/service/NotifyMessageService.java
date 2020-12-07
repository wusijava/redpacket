package com.zanclick.redpacket.api.service;



import com.zanclick.redpacket.api.entity.NotifyMessage;
import com.zanclick.redpacket.common.base.service.BaseService;

import java.util.List;

/**
 * @author long5
 * @date 2020-08-25 17:28:24
 **/
public interface NotifyMessageService extends BaseService<NotifyMessage, Long> {

    /**
     * 查询等待推送的消息
     *
     * @return 消息列表
     */
    List<NotifyMessage> queryWaitSendMessage();

    /**
     * 发送失败
     * @param id ID
     * */
    void sendFailed(Long id);

    /**
     * 发送失败调整信息
     * @param message 信息
     * */
    void change(NotifyMessage message);

    /**
     * 发送成功 删除记录
     * @param id ID
     * */
    void sendSuccess(Long id);
}
