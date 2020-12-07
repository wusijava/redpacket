package com.zanclick.redpacket.api.service.impl;


import com.zanclick.redpacket.api.entity.NotifyMessage;
import com.zanclick.redpacket.api.mapper.NotifyMessageMapper;
import com.zanclick.redpacket.api.query.NotifyMessageQuery;
import com.zanclick.redpacket.api.service.NotifyMessageService;
import com.zanclick.redpacket.common.base.dao.mybatis.BaseMapper;
import com.zanclick.redpacket.common.base.service.impl.BaseMybatisServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author long5
 * @date 2020-08-25 17:28:24
 **/
@Service
public class NotifyMessageServiceImpl extends BaseMybatisServiceImpl<NotifyMessage,Long> implements NotifyMessageService {

    private final NotifyMessageMapper notifyMessageMapper;

    @Autowired
    public NotifyMessageServiceImpl(NotifyMessageMapper notifyMessageMapper) {
        this.notifyMessageMapper = notifyMessageMapper;
    }

    @Override
    protected BaseMapper<NotifyMessage, Long> getBaseMapper() {
        return notifyMessageMapper;
    }

    @Override
    public List<NotifyMessage> queryWaitSendMessage() {
        NotifyMessageQuery query = new NotifyMessageQuery();
        query.setCurrDate(new Date());
        query.setState(NotifyMessage.State.WAIT.getCode());
        return this.queryList(query, PageRequest.of(0,100));
    }

    @Override
    public void sendFailed(Long id) {
        NotifyMessage message = new NotifyMessage();
        message.setId(id);
        message.setState(NotifyMessage.State.FAIL.getCode());
        getBaseMapper().updateById(message);
    }

    @Override
    public void change(NotifyMessage message) {
        getBaseMapper().updateById(message);
    }

    @Override
    public void sendSuccess(Long id) {
        getBaseMapper().deleteById(id);
    }
}
