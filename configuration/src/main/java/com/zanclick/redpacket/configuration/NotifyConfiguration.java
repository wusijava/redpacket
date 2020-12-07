package com.zanclick.redpacket.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * @author duchong
 * @description 异步回调的相关配置
 * @date 2020-8-24 16:32:59
 */
@Data
@Component
@ConfigurationProperties(prefix = "notify.send")
@PropertySource(value = {"classpath:configuration.properties"})
public class NotifyConfiguration {

    /**
     * 异步回调的通知次数及时间间隔，单位分钟
     */
    private Integer[] times;

    /**
     * 异步通知轮询间隔，单位毫秒
     */
    private Long millisecond;


    /**
     * 异步回调的 字符串编码
     */
    private String charset;

    /**
     * 异步回调的 签名方式
     */
    private String signType;
}
