package com.zanclick.redpacket.executor.controller;

import com.alibaba.fastjson.JSON;
import com.zanclick.redpacket.executor.service.TaskExecutorManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * @author zanclick
 */
@RestController
@RequestMapping("/task/executors")
@ConditionalOnBean(TaskExecutorManager.class)
public class TaskExecutorManagerController {

    @Autowired
    TaskExecutorManager taskExecutorManager;

    @GetMapping("/dump")
    public String dumpThreadPoolInfo() {
        return JSON.toJSONString(taskExecutorManager.getExecutorsStatus(),true);
    }

}
