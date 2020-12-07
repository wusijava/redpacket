package com.zanclick.redpacket.api.query;

import com.zanclick.redpacket.api.entity.NotifyMessage;
import lombok.Data;

import java.util.Date;

/**
 * @author duchong
 * @description 异步消息（首次推送成功不会进这里）
 * @date 2019-11-26 09:50:23
 */
@Data
public class NotifyMessageQuery extends NotifyMessage {

    private static final long serialVersionUID = -7500200973548687975L;

    /**
     * 当前推送的时间
     */
    private Date currDate;


}
