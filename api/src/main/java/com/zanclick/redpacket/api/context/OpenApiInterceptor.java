package com.zanclick.redpacket.api.context;

import com.zanclick.redpacket.api.util.ApiRequestUtil;
import com.zanclick.redpacket.common.enums.ExceptionHandlerEnum;
import com.zanclick.redpacket.common.utils.ApplicationContextHelper;
import com.zanclick.redpacket.common.utils.IpUtils;
import com.zanclick.redpacket.configuration.entity.App;
import com.zanclick.redpacket.configuration.entity.IpControl;
import com.zanclick.redpacket.configuration.service.AppService;
import com.zanclick.redpacket.configuration.service.IpControlService;
import com.zanclick.redpacket.sdk.ApiRequestParam;
import com.zanclick.redpacket.sdk.enums.ApiErrorEnum;
import com.zanclick.redpacket.sdk.exception.BaseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author duchong
 * @description 开放接口拦截器
 * @date 2019-12-6 18:03:40
 */
public class OpenApiInterceptor implements HandlerInterceptor {

    @Autowired
    private AppService appService;

    private IpControlService ipControlService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        request.setAttribute(ExceptionHandlerEnum.EXCEPTION_HANDLER_SIGN, ExceptionHandlerEnum.OPEN_API_SERVICE);
        ApiRequestParam commonRequest = ApiRequestUtil.getRequest();
        String check = commonRequest.check();
        if (check != null){
            throw new BaseException(ApiErrorEnum.MISSING_PUBLIC_PARAM,check);
        }
        if (appService == null){
            appService = ApplicationContextHelper.getBean(AppService.class);
        }
        if (ipControlService == null){
            ipControlService = ApplicationContextHelper.getBean(IpControlService.class);
        }
        //App app = appService.queryByAppId(commonRequest.getApp_id());
        App a=new App();
        a.setId(5L);

        List<IpControl> ipControls = ipControlService.queryByAppId("TEST202005041652231204911");


        App app=appService.queryByAppId("TEST202005041652231204911");
        if (app == null || app.getState().equals(App.State.CLOSED.getCode())){
            throw new BaseException(ApiErrorEnum.INVALID_PUBLIC_PARAM,"app_id无效");
        }
        if (App.IpControl.OPEN.getCode().equals(app.getIpControl())){
            List<IpControl> ipControlList = ipControlService.queryByAppId(app.getAppId());
            if (!ipControlList.contains(new IpControl(IpUtils.getIpAddress(request)))){
                throw new BaseException(ApiErrorEnum.INVALID_IP_ADDRESS);
            }
        }
        ApiRequestUtil.setCheckSign(app.getCheckSign().equals(App.CheckSign.OPEN.getCode()));
        ApiRequestUtil.setPrivateKey(app.getPrivateKey());
        ApiRequestUtil.setPublicKey(app.getAppPublicKey());
        return true;
    }
}
