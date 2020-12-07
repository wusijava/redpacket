package com.zanclick.redpacket.api.gateway;

import com.zanclick.redpacket.api.client.ApiClient;
import com.zanclick.redpacket.api.notify.AsyncNotifySender;
import com.zanclick.redpacket.api.util.ApiRequestUtil;
import com.zanclick.redpacket.api.util.SystemClock;
import com.zanclick.redpacket.common.swagger.annotation.OpenV1Api;
import com.zanclick.redpacket.common.utils.HttpUtils;
import com.zanclick.redpacket.common.utils.JsonUtils;
import com.zanclick.redpacket.sdk.ZcApiResult;
import com.zanclick.redpacket.sdk.util.SignUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Slf4j
@Api(tags = "对外接口统一地址")
@OpenV1Api
@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
public class GatewayController {

    private final ApiClient apiClient;

    private final AsyncNotifySender messageSender;

    @Autowired
    public GatewayController(ApiClient apiClient, AsyncNotifySender messageSender) {
        this.apiClient = apiClient;
        this.messageSender = messageSender;
    }

    /**
     * 统一网关入口
     *
     * @param method    请求方法
     * @param version   版本
     * @param requestId 请求标识（用于日志中分辨是否是同一次请求）
     * @param charset   请求编码
     * @param signType  签名格式
     * @param sign      签名
     * @param content   业务内容参数
     * @author 码农猿
     */
    @ApiOperation(value = "对外接口网关")
    @PostMapping("/gateway.do")
    public ZcApiResult gateway(@RequestParam(value = "app_id", required = true) String appId,
                               @RequestParam(value = "method", required = true) String method,
                               @RequestParam(value = "timestamp", required = true) String timestamp,
                               @RequestParam(value = "notify_url", required = false) String notifyUrl,
                               @RequestParam(value = "version", required = true) String version,
                               @RequestParam(value = "request_id", required = true) String requestId,
                               @RequestParam(value = "charset", required = true) String charset,
                               @RequestParam(value = "sign_type", required = true) String signType,
                               @RequestParam(value = "sign", required = true) String sign,
                               @RequestParam(value = "content", required = false) String content,
                               HttpServletRequest request) throws Throwable {

        Map<String, String> params = HttpUtils.getRequestParams(request);
        log.info("【{}】>> 网关执行开始 >> method={} params = {}", requestId, method, JsonUtils.toJson(params));
        long start = SystemClock.millisClock().now();
        //验签
        if (ApiRequestUtil.getCheckSign()){
            apiClient.checkSign(params, ApiRequestUtil.getPublicKey());
        }
        //请求接口
        ZcApiResult result = apiClient.invoke(content);

        SignUtils.sign(result, ApiRequestUtil.getPrivateKey());

        log.info("【{}】>> 网关执行结束 >> method={},result = {}, times = {} ms",
                requestId, method, JsonUtils.toJson(result), (SystemClock.millisClock().now() - start));
        return result;
    }



//    @ApiOperation(value = "测试")
//    @PostMapping("/sendMessage")
//    public String sendMessage(HttpServletRequest request) throws Throwable {
//        JSONObject object = new JSONObject();
//        object.put("authNo", "2141412");
//        object.put("status", "2313131");
//        object.put("outTradeNo", "312312313");
//        messageSender.sendMessage(ApiMethod.CAPITAL_TRADE_REPAY_MESSAGE, object.toJSONString(), "http:127.0.0.1:8087/api/open/yyf/notify", "TEST202005041652231204912");
//
//        return "{\"code\":\"20000\"}";
//    }

}
