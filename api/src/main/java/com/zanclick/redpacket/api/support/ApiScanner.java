package com.zanclick.redpacket.api.support;



import com.zanclick.redpacket.api.anonation.OpenApi;
import com.zanclick.redpacket.api.anonation.OpenApiService;
import com.zanclick.redpacket.common.utils.ApplicationContextHelper;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

import javax.annotation.Resource;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Api接口扫描器
 */
@Component
public class ApiScanner implements CommandLineRunner {


    private static final Logger LOGGER = LoggerFactory.getLogger(ApiScanner.class);

    /**
     * 方法签名拆分正则
     */
    private static final Pattern PATTERN = Pattern.compile("\\s+(.*)\\s+((.*)\\.(.*))\\((.*)\\)", Pattern.DOTALL);

    /**
     * 参数分隔符
     */
    private static final String PARAMS_SEPARATOR = ",";

    /**
     * 统计扫描次数
     */
    private AtomicInteger atomicInteger = new AtomicInteger(0);

    @Resource
    private ApiContainer apiContainer;

    @Override
    public void run(String... var1) throws Exception {
        //扫描所有使用@OpenApiService注解的类
        Map<String, Object> openApiServiceBeanMap = ApplicationContextHelper.getBeansWithAnnotation(OpenApiService.class);

        if (null == openApiServiceBeanMap || openApiServiceBeanMap.isEmpty()) {
            LOGGER.info("open api service bean map is empty");
            return;
        }

        for (Map.Entry<String, Object> map : openApiServiceBeanMap.entrySet()) {
            //获取扫描类下所有方法
            Method[] methods = ReflectionUtils.getAllDeclaredMethods(map.getValue().getClass());
            for (Method method : methods) {
                atomicInteger.incrementAndGet();
                //找到带有OpenApi 注解的方法
                OpenApi openApi = AnnotationUtils.findAnnotation(method, OpenApi.class);

                if (null == openApi) {
                    continue;
                }
                //获取业务参数对象
                String paramName = getParamName(method);
                if (StringUtils.isBlank(paramName)) {
                    LOGGER.warn("Api接口业务参数缺失 >> method = {}", openApi.api().getMethod());
                    continue;
                }

                //组建ApiModel- 放入api容器
                apiContainer.put(openApi.api().getMethod(), openApi.api().getVersion(), new ApiModel(map.getKey(), method, paramName));
                LOGGER.info("Api接口加载成功 >> method = {} , version = {} , desc={}", openApi.api().getMethod(), openApi.api().getVersion(), openApi.api().getDesc());
            }
        }
        LOGGER.info("Api接口容器加载完毕 >> size = {} loopTimes={}", apiContainer.size(), atomicInteger.get());
    }

    /**
     * 获取业务参数对象
     *
     * @param method
     * @return
     */
    private String getParamName(Method method) {
        ArrayList<String> result = new ArrayList<>();
        final Matcher matcher = PATTERN.matcher(method.toGenericString());
        if (matcher.find()) {
            int groupCount = matcher.groupCount() + 1;
            for (int i = 0; i < groupCount; i++) {
                result.add(matcher.group(i));
            }
        }
        if (result.size() >= 6) {
            String[] params =
                    StringUtils.splitByWholeSeparatorPreserveAllTokens(result.get(5), PARAMS_SEPARATOR);
            return params[0];
        }
        return null;
    }

}
