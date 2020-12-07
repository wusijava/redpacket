package com.zanclick.redpacket.configuration.async;


import com.zanclick.redpacket.executor.service.TaskExecutorManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.scheduling.annotation.AsyncConfigurer;

/**
 * @author duchong
 * @description 异步消息推送配置
 * @date 2020-8-25 14:54:16
 */
@Slf4j
@Configuration
public class AsyncConfiguration implements AsyncConfigurer {

    private final TaskExecutorManager taskExecutorManager;

    @Autowired
    public AsyncConfiguration(TaskExecutorManager taskExecutorManager) {
        this.taskExecutorManager = taskExecutorManager;
    }

    @Bean(name = "notifyExecutor")
    public AsyncTaskExecutor notifyExecutor() {
        return taskExecutorManager.getExecutor("notify");
    }


    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return (ex, method, params) -> log.error(String.format("执行异步任务'%s'", method), ex);
    }
}
