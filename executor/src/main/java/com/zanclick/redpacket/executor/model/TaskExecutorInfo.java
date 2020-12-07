package com.zanclick.redpacket.executor.model;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class TaskExecutorInfo {

    int activeCount;

    int poolSize;

    int corePoolSize;

    int keepAliveSeconds;

    int maxPoolSize;

    long taskCount;

    int queueSize;

    int queueRemainingCapacity;

}
