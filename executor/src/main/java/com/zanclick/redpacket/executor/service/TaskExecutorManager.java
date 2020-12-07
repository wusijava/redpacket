package com.zanclick.redpacket.executor.service;

import com.zanclick.redpacket.executor.config.ExecutorProperties;
import com.zanclick.redpacket.executor.config.ExecutorsProperties;
import com.zanclick.redpacket.executor.model.TaskExecutorInfo;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;


/**
 * @author zanclick
 */
@Slf4j
@Data
public class TaskExecutorManager {

    final Map<String, ThreadPoolTaskExecutor> executorMap = new HashMap<>();

    private synchronized void registerExecutor(String threadPoolName, ThreadPoolTaskExecutor executor) {
        if (executorMap.containsKey(threadPoolName)) {
            log.error("名称为{}的线程池已存在，无法注册到线程池管理服务...", threadPoolName);
        } else {
            executorMap.put(threadPoolName, executor);
        }
    }

    /**
     * 创建并注册线程池
     *
     * @param threadPoolName
     * @param properties
     * @return
     */
    public ThreadPoolTaskExecutor createAndRegisterExecutor(String threadPoolName, ExecutorProperties properties) {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setThreadNamePrefix(DEFAULT_EXECUTOR_NAME + "-" + threadPoolName);
        executor.setCorePoolSize(properties.getCorePoolSize());
        executor.setMaxPoolSize(properties.getMaxPoolSize());
        executor.setQueueCapacity(properties.getQueueCapacity());
        executor.setKeepAliveSeconds(properties.getKeepAliveSeconds());
        RejectedExecutionHandler rejectedHandler;
        try {
            rejectedHandler = properties.getRejectedHandler().newInstance();
        } catch (Exception e) {
            log.error("【[{}】无法加载rejectedHandler【{}】", threadPoolName, properties.getRejectedHandler());
            rejectedHandler = new ThreadPoolExecutor.CallerRunsPolicy();
        }
        executor.setRejectedExecutionHandler(rejectedHandler);
        registerExecutor(threadPoolName, executor);
        executor.initialize();
        return executor;
    }

    ExecutorsProperties executorsProperties;

    public static final String DEFAULT_EXECUTOR_NAME = "TaskExecutor";

    public void initExecutors() {
        ExecutorProperties defaultProperties = (executorsProperties.getExecutor() == null) ?
                new ExecutorProperties() : executorsProperties.getExecutor();
        log.info("初始化默认线程池：{}", (defaultProperties));
        createAndRegisterExecutor(DEFAULT_EXECUTOR_NAME, defaultProperties);
        executorsProperties.getExecutors().forEach((name, properties) -> {
            log.info("初始化线程池{}:{}", name, properties);
            createAndRegisterExecutor(name, properties);
        });
    }

    public ThreadPoolTaskExecutor getExecutor(String threadPoolName) {
        return executorMap.get(threadPoolName);
    }

    public Map<String, TaskExecutorInfo> getExecutorsStatus() {
        Map<String, TaskExecutorInfo> resultMap = new HashMap<>(executorMap.size());
        executorMap.forEach((name, executor) -> {
            resultMap.put(name, dumpExecutorInfo(executor));
        });
        return resultMap;
    }

    private TaskExecutorInfo dumpExecutorInfo(ThreadPoolTaskExecutor executor) {
        TaskExecutorInfo.TaskExecutorInfoBuilder infoBuilder = TaskExecutorInfo.builder()
                .activeCount(executor.getActiveCount())
                .poolSize(executor.getPoolSize())
                .corePoolSize(executor.getCorePoolSize())
                .keepAliveSeconds(executor.getKeepAliveSeconds())
                .maxPoolSize(executor.getMaxPoolSize());
        ThreadPoolExecutor internalExecutor = null;
        try {
            internalExecutor = executor.getThreadPoolExecutor();
        } catch (IllegalStateException e) {
        }
        if (internalExecutor != null) {
            infoBuilder.taskCount(executor.getThreadPoolExecutor().getTaskCount())
                    .queueSize(executor.getThreadPoolExecutor().getQueue().size())
                    .queueRemainingCapacity(executor.getThreadPoolExecutor().getQueue().remainingCapacity());
        }
        return infoBuilder.build();
    }

}
