package com.zanclick.redpacket.executor.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zanclick
 */
@Data
@ConfigurationProperties("zcpay.task.executor")
public class ExecutorsProperties {

    ExecutorProperties executor;

    Map<String, ExecutorProperties> executors = new HashMap<>();
}
