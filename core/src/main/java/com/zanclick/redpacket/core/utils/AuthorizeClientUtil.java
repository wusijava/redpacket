package com.zanclick.redpacket.core.utils;

import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayClient;
import com.alipay.api.request.*;
import com.alipay.api.response.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 预授权支付工具类
 *
 * @author duchong
 * @date 2019-7-9 10:44:42
 */
public class AuthorizeClientUtil {

    private static Logger _log = LoggerFactory.getLogger(AuthorizeClientUtil.class);



    /**
     * 二单笔转账
     *
     * @param client
     * @param biz_content
     * @param appAuthToken
     * @return
     */
    public static AlipayFundTransToaccountTransferResponse transfer(AlipayClient client, String appAuthToken, String biz_content){
        AlipayFundTransToaccountTransferRequest request = new AlipayFundTransToaccountTransferRequest();
        request.setBizContent(biz_content);
        AlipayFundTransToaccountTransferResponse response = null;
        try {
            _log.error("单笔转账:{}", JSONObject.toJSONString(request));
            response = client.execute(request, null, appAuthToken);
        }catch (Exception e){
            _log.error("单笔转账：{}", e);
        }
        if (response == null) {
            response = new AlipayFundTransToaccountTransferResponse();
            response.setMsg("请求失败");
            response.setSubCode("SYSTEM_ERROR");
            response.setSubMsg("系统繁忙，稍后再试");
        }
        return response;
    }

}
