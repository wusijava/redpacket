package com.zanclick.redpacket.api.util;


import com.zanclick.redpacket.api.context.RequestContext;
import com.zanclick.redpacket.sdk.ApiRequestParam;

/**
 * @author duchong
 * @description 接口请求参数寄存
 * @date 2020-8-22 11:32:21
 */
public class ApiRequestUtil {

    static String appId = "app_id";

    static String charset = "charset";

    static String method = "method";

    static String signType = "sign_type";

    static String requestId = "request_id";

    static String version = "version";

    static String sign = "sign";

    static String timestamp = "timestamp";

    static String notifyUrl = "notify_url";

    static String content = "content";

    static String privateKey = "private_key";

    static String publicKey = "public_key";

    static String checkSign = "check_sign";

    public static String getAppId() {
        return getStringValue(appId);
    }

    public static void setAppId(String appId) {
        RequestContext.getRequest().setAttribute(ApiRequestUtil.appId, appId);
    }

    public static String getCharset() {
        return getStringValue(charset);
    }

    public static String getMethod() {
        return getStringValue(method);
    }

    public static void setMethod(String method) {
        RequestContext.getRequest().setAttribute(method, method);
    }

    public static String getSignType() {
        return getStringValue(signType);
    }

    public static String getRequestId() {
        return getStringValue(requestId);
    }

    public static void setRequestId(String requestId) {
        RequestContext.getRequest().setAttribute(ApiRequestUtil.requestId, requestId);
    }

    public static String getVersion() {
        return getStringValue(version);
    }

    public static String getSign() {
        return getStringValue(sign);
    }

    public static String getTimestamp() {
        return getStringValue(timestamp);
    }

    public static String getNotify_url() {
        return getStringValue(notifyUrl);
    }

    public static String getContent() {
        return getStringValue(content);
    }

    public static String getPrivateKey() {
        Object object = RequestContext.getRequest().getAttribute(privateKey);
        return object == null ? null : (String) object;
    }

    public static void setPrivateKey(String private_key) {
        RequestContext.getRequest().setAttribute(privateKey, private_key);
    }

    public static String getPublicKey() {
        Object object = RequestContext.getRequest().getAttribute(publicKey);
        return object == null ? null : (String) object;
    }

    public static void setPublicKey(String public_key) {
        RequestContext.getRequest().setAttribute(publicKey, public_key);
    }

    public static Boolean getCheckSign() {
        Object object = RequestContext.getRequest().getAttribute(checkSign);
        return object == null ? true : (Boolean) object;
    }

    public static void setCheckSign(Boolean checkSign) {
        RequestContext.getRequest().setAttribute(ApiRequestUtil.checkSign, checkSign);
    }

    public static ApiRequestParam getRequest() {
        ApiRequestParam commonRequest = new ApiRequestParam();
        commonRequest.setApp_id(getAppId());
        commonRequest.setCharset(getCharset());
        commonRequest.setContent(getContent());
        commonRequest.setMethod(getMethod());
        commonRequest.setNotify_url(getNotify_url());
        commonRequest.setTimestamp(getTimestamp());
        commonRequest.setRequest_id(getRequestId());
        commonRequest.setSign(getSign());
        commonRequest.setSign_type(getSignType());
        commonRequest.setVersion(getVersion());
        return commonRequest;
    }

    public static String getStringValue(String key) {
        Object object = RequestContext.getRequest().getParameter(key);
        return object == null ? null : String.valueOf(object);
    }
}
