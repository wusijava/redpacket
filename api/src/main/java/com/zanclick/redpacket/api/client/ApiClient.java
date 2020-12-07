package com.zanclick.redpacket.api.client;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.zanclick.redpacket.api.support.ApiContainer;
import com.zanclick.redpacket.api.support.ApiModel;
import com.zanclick.redpacket.api.util.ApiRequestUtil;
import com.zanclick.redpacket.api.util.ValidateUtils;
import com.zanclick.redpacket.common.utils.ApplicationContextHelper;
import com.zanclick.redpacket.common.utils.JsonUtils;
import com.zanclick.redpacket.sdk.ZcApiResult;
import com.zanclick.redpacket.sdk.ZcApiResultBuilder;
import com.zanclick.redpacket.sdk.enums.ApiErrorEnum;
import com.zanclick.redpacket.sdk.exception.BusinessException;
import com.zanclick.redpacket.sdk.util.ApiUtils;
import com.zanclick.redpacket.sdk.util.RSAUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;

/**
 * Api请求客户端
 *
 * @author duchong
 * @date 2020-6-5 18:20:27
 */
@Slf4j
@Service
public class ApiClient {

    /**
     * 日志
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(ApiClient.class);

    /**
     * jackson 序列化工具类
     */
    private static final ObjectMapper JSON_MAPPER = new ObjectMapper();

    /**
     * Api本地容器
     */
    private final ApiContainer apiContainer;

    public ApiClient(ApiContainer apiContainer) {
        this.apiContainer = apiContainer;
    }

    /**
     * 验签
     *
     * @param params    请求参数
     * @param publicKey 私钥
     */
    public void checkSign(Map<String, String> params, String publicKey) {
        try {
            log.warn("【{}】>> 验签参数 {}", ApiRequestUtil.getRequestId(), params);

            boolean checkSign = RSAUtils.rsaCheck(params, publicKey);

            if (!checkSign) {
                LOGGER.info("【{}】>> 验签失败 >> params = {}", ApiRequestUtil.getRequestId(), JsonUtils.toJson(params));
                throw new BusinessException(ApiErrorEnum.INVALID_SIGN);
            }

            LOGGER.warn("【{}】>> 验签成功", ApiRequestUtil.getRequestId());
        } catch (Exception e) {
            LOGGER.error("【{}】>> 验签异常 >> params = {}, error = {}",
                    ApiRequestUtil.getRequestId(), params, ExceptionUtils.getStackTrace(e));
            throw new BusinessException(ApiErrorEnum.INVALID_SIGN);
        }

    }


    /**
     * Api调用方法
     *
     * @param content
     * @author 码农猿
     */
    public ZcApiResult invoke(String content) throws Throwable {
        //获取api方法
        ApiModel apiModel = apiContainer.get(ApiRequestUtil.getMethod(), ApiRequestUtil.getVersion());
        if (null == apiModel) {
            LOGGER.info("【{}】>> API方法不存在 >> method = {}", ApiRequestUtil.getRequestId(), ApiRequestUtil.getMethod());
            throw new BusinessException(ApiErrorEnum.API_NOT_EXIST, ApiRequestUtil.getMethod());
        }

        //获得spring bean
        Object bean = ApplicationContextHelper.getBean(apiModel.getBeanName());
        if (null == bean) {
            LOGGER.warn("【{}】>> API方法不存在 >> method = {}, beanName = {}", ApiRequestUtil.getRequestId(), ApiRequestUtil.getMethod(), apiModel.getBeanName());
            throw new BusinessException(ApiErrorEnum.API_NOT_EXIST, ApiRequestUtil.getMethod());
        }

        //处理业务参数
        // 忽略JSON字符串中存在，而在Java中不存在的属性
        JSON_MAPPER.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        // 设置下划线序列化方式
        JSON_MAPPER.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
        Object result = JSON_MAPPER.readValue(content, Class.forName(apiModel.getParamName()));

        //校验参数
        ValidateUtils.validate(result);

        //执行对应方法
        try {
            Object obj = apiModel.getMethod().invoke(bean, result);
            ZcApiResult model = ZcApiResultBuilder.success();
            model.setTimestamp(ApiUtils.getTimestamp());
            model.setVersion(ApiRequestUtil.getVersion());
            model.setSign_type(ApiRequestUtil.getSignType());
            model.setRequest_id(ApiRequestUtil.getRequestId());
            model.setMethod(ApiRequestUtil.getMethod());
            model.setContent(obj == null ? null : JsonUtils.toJson(obj));
            model.setApp_id(ApiRequestUtil.getAppId());
            model.setCharset(ApiRequestUtil.getCharset());
            return model;
        } catch (Exception e) {
            if (e instanceof InvocationTargetException) {
                throw ((InvocationTargetException) e).getTargetException();
            }
            throw new BusinessException(ApiErrorEnum.SYSTEM_ERROR);
        }

    }


}
