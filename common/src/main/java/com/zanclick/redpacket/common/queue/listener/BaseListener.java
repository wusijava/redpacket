package com.zanclick.redpacket.common.queue.listener;

import com.alibaba.fastjson.JSONObject;

import com.zanclick.redpacket.common.jms.SendMessage;
import com.zanclick.redpacket.common.queue.contents.DelayTimeContents;
import com.zanclick.redpacket.common.utils.DataUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BaseListener {

    private final static Logger _log = LoggerFactory.getLogger(BaseListener.class);

    /**
     * 重发消息队列，最多重发三次
     * @param object        队列数据
     * @param queue_name    队列名称
     * @param longTime      是否采用长延时
     */
    public void sendMsgAgain(JSONObject object, String queue_name, Object...longTime) {
        Integer count = 0;
        if (object.containsKey("count")) {
            count = object.getInteger("count");
        }
        if (count < 3) {
            object.put("count", count + 1);
            if (!DataUtils.isEmpty(longTime)) {
                SendMessage.sendDelayMessage(queue_name, object.toJSONString(), DelayTimeContents.DELAYED_TIMES_LONG[count]);
            } else {
                SendMessage.sendDelayMessage(queue_name, object.toJSONString(), DelayTimeContents.DELAYED_TIMES_SHORT[count]);
            }
            _log.error(queue_name + "失败第" + (count + 1) + "次");
        } else {
            _log.error(queue_name + "失败三次");
        }
    }

}
