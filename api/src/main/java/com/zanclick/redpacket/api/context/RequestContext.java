package com.zanclick.redpacket.api.context;

import javax.servlet.http.HttpServletRequest;

/**
 * @author duchong
 * @description 开放接口请求上下文
 * @date 2020-8-22 11:29:45
 */
public class RequestContext {

    private static final ThreadLocal<RequestContext> THREAD_LOCAL = new ThreadLocal<RequestContext>();

    private HttpServletRequest request;

    private RequestContext() {
    }

    public static void init(HttpServletRequest request) {
        THREAD_LOCAL.remove();
        RequestContext context = new RequestContext();
        context.request = request;
        THREAD_LOCAL.set(context);
    }

    public static RequestContext getContext() {
        return THREAD_LOCAL.get();
    }

    public static HttpServletRequest getRequest() {
        RequestContext context = getContext();
        if (context == null) {
            return null;
        }
        return context.request;
    }

    public static void clear() {
        THREAD_LOCAL.remove();
    }
}
