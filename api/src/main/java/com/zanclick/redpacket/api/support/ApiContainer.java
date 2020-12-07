package com.zanclick.redpacket.api.support;

import org.springframework.stereotype.Service;

import java.util.HashMap;

/**
 * Api 初始化容器
 */
@Service
public class ApiContainer extends HashMap<String, ApiModel> {

    /**
     * 放入扫描到的方法
     *
     * @param method  方法名称
     * @param version 版本号
     * @param model   方法体
     */
    public void put(String method, String version, ApiModel model) {
        this.put(method + "-" + version, model);
    }

    /**
     * 放入扫描到的方法
     *
     * @param method  方法名称
     * @param version 版本号
     * @return apiModel
     */
    public ApiModel get(String method, String version) {
        return this.get(method + "-" + version);
    }
}
