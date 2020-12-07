package com.zanclick.redpacket.api.exception;


import com.zanclick.redpacket.api.util.ApiRequestUtil;
import com.zanclick.redpacket.common.enums.ExceptionHandlerEnum;
import com.zanclick.redpacket.common.exception.BusinessException;
import com.zanclick.redpacket.common.exception.ExceptionHandlerResolver;
import com.zanclick.redpacket.sdk.ZcApiResult;
import com.zanclick.redpacket.sdk.ZcApiResultBuilder;
import com.zanclick.redpacket.sdk.enums.ApiErrorEnum;
import com.zanclick.redpacket.sdk.exception.BaseException;
import com.zanclick.redpacket.sdk.util.ApiUtils;
import com.zanclick.redpacket.sdk.util.SignUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component(value = ExceptionHandlerEnum.OPEN_API_SERVICE)
public class OpenApiExceptionHandler implements ExceptionHandlerResolver<ZcApiResult> {

    @Override
    public ZcApiResult handler(Exception exception) {
        ZcApiResult result;
        try {
            throw exception;
        } catch (BusinessException e) {
            log.error("接口处理异常 >> error = {},{}",  e.getCode(),e.getMsg());
            result = ZcApiResultBuilder.fail(e.getCode(), e.getMsg());
        } catch (BaseException e) {
            log.error("接口处理异常 >> error = {},{}",  e.getCode(),e.getMsg());
            result = ZcApiResultBuilder.error(e.getCode(), e.getMsg());
        } catch (Exception e) {
            log.error("接口处理异常 >> error = ",  e);
            result = ZcApiResultBuilder.error(ApiErrorEnum.SYSTEM_ERROR.getCode(), ApiErrorEnum.SYSTEM_ERROR.getMsg());
        }
        result.setApp_id(ApiRequestUtil.getAppId());
        result.setCharset(ApiRequestUtil.getCharset());
        result.setMethod(ApiRequestUtil.getMethod());
        result.setRequest_id(ApiRequestUtil.getRequestId());
        result.setSign_type(ApiRequestUtil.getSignType());
        result.setVersion(ApiRequestUtil.getVersion());
        result.setTimestamp(ApiUtils.getTimestamp());
        SignUtils.sign(result, ApiRequestUtil.getPrivateKey());
        return result;
    }
}
