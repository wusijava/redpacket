package com.zanclick.redpacket.common.exception;


import com.zanclick.redpacket.common.enums.ExceptionHandlerEnum;
import com.zanclick.redpacket.common.model.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.text.MessageFormat;

@Slf4j
@Component(value = ExceptionHandlerEnum.COMMON_API_SERVICE)
public class CommonExceptionHandler implements ExceptionHandlerResolver<Result<String>> {

    @Override
    public Result<String> handler(Exception exception) {
        Result result;
        try {
            log.error("全局业务处理异常 >> error = {}", exception.getMessage(), exception);
            throw exception;
        } catch (BusinessException e) {
            result = Result.fail(e.getMsg());
        } catch (BizException e) {
            result = Result.fail(e.getMessage());
        } catch (BindException e) {
            BindingResult br = e.getBindingResult();
            FieldError error = br.getFieldError();
            assert error != null;
            result = Result.fail(error.getDefaultMessage());
        } catch (IllegalArgumentException e) {
            String errorMsg = MessageFormat.format(CommonExceptionEnum.INVALID_PARAM.getMsg(), e.getMessage());
            result = Result.fail(errorMsg);
        } catch (MethodArgumentTypeMismatchException e) {
            String errorMsg = MessageFormat.format(CommonExceptionEnum.INVALID_PARAM_TYPE.getMsg(), e.getName());
            result = Result.fail(errorMsg);
        } catch (MissingServletRequestParameterException e) {
            String errorMsg = MessageFormat.format(CommonExceptionEnum.MISSING_PARAM.getMsg(), e.getMessage());
            result = Result.fail(errorMsg);
        } catch (HttpRequestMethodNotSupportedException e) {
            String errorMsg = MessageFormat.format(CommonExceptionEnum.INVALID_REQUEST_ERROR.getMsg(), e.getMethod(), e.getSupportedHttpMethods());
            result = Result.error(errorMsg);
        } catch (Exception e) {
            result = Result.error(CommonExceptionEnum.SYSTEM_ERROR.getMsg());
        }
        return result;
    }
}
