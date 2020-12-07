package com.zanclick.redpacket.common.exception;


import com.zanclick.redpacket.common.base.controller.BaseController;
import com.zanclick.redpacket.common.enums.ExceptionHandlerEnum;
import com.zanclick.redpacket.common.utils.ApplicationContextHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author duchong
 * @description 统一异常处理
 * @date 2020-5-27 15:31:57
 */
@Slf4j
@ControllerAdvice
@EnableAspectJAutoProxy
public class ControllerExceptionHandler extends BaseController {

    @CrossOrigin
    @ResponseBody
    @ExceptionHandler(value = Exception.class)
    public Object defaultExceptionHandler(Exception exception) {
        String serviceName = getUnifiedExceptionHandlerName();
        ExceptionHandlerResolver resolver = (ExceptionHandlerResolver) ApplicationContextHelper.getBean(serviceName);
        assert resolver != null;
        return resolver.handler(exception);
    }


    /**
     * 获取统一异常处理标记的服务名称，目前默认处理开放接口的异常
     *
     * @return 服务名称
     */
    protected String getUnifiedExceptionHandlerName() {
        Object object = getRequest().getAttribute(ExceptionHandlerEnum.EXCEPTION_HANDLER_SIGN);
        return object == null ? ExceptionHandlerEnum.OPEN_API_SERVICE : (String) object;
    }
}
