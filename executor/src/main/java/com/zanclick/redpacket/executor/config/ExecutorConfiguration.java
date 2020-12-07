package com.zanclick.redpacket.executor.config;

import com.zanclick.redpacket.executor.service.TaskExecutorManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 * @author zanclick
 */
@Configuration
@EnableConfigurationProperties(ExecutorsProperties.class)
@ComponentScan("com.zanclick.redpacket.executor")
public class ExecutorConfiguration {

    public ExecutorConfiguration(){

    }

    @Autowired
    ExecutorsProperties executorsProperties;

    @Bean
    public TaskExecutorManager taskExecutorManager() {
        TaskExecutorManager executorManager = new TaskExecutorManager();
        executorManager.setExecutorsProperties(executorsProperties);
        executorManager.initExecutors();
        return executorManager;
    }

    @Bean
    public ThreadPoolTaskExecutor defaultTaskExecutor(TaskExecutorManager executorManager) {
        return executorManager.getExecutor(TaskExecutorManager.DEFAULT_EXECUTOR_NAME);
    }

 //TODO 这里临时注释一下
//    @Bean
//    public TaskExecutorManagerController executorController() {
//        return new TaskExecutorManagerController();
//    }

}
