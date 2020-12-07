package com.zanclick.redpacket.executor.config;

import lombok.Data;

import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author zanclick
 */
@Data
public class ExecutorProperties {

    int corePoolSize = 10;

    int maxPoolSize = 50;

    int queueCapacity = 100;

    int keepAliveSeconds = 300;

    Class<? extends RejectedExecutionHandler> rejectedHandler = ThreadPoolExecutor.CallerRunsPolicy.class;

}
