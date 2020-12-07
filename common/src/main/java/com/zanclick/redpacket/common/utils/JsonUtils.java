package com.zanclick.redpacket.common.utils;


import com.alibaba.fastjson.JSONObject;

import java.util.List;

/**
 * JSON工具类 (统一用fastJson)
 *
 * @author duchong
 * @date 2019-7-24 11:24:06
 */
public class JsonUtils {


    /**
     * 转JSON字符串（不能格式化）
     *
     * @param obj
     * @return
     */
    public static String toJson(Object obj) {
        return JSONObject.toJSONString(obj);
    }

    /**
     * 转JSON字符串
     *
     * @param jsonStr json字符串
     * @param tClass  需要转换的类
     * @return
     */
    public static <T> T parseToJava(String jsonStr, Class<T> tClass) {
        return JSONObject.parseObject(jsonStr, tClass);
    }

    /**
     * 转JSON字符串
     *
     * @param jsonStr json字符串
     * @param tClass  需要转换的类
     * @return
     */
    public static <T> List<T> parseToArray(String jsonStr, Class<T> tClass) {
        return JSONObject.parseArray(jsonStr, tClass);
    }

    /**
     * 转JSON字符串
     *
     * @param jsonStr json字符串
     * @return
     */
    public static JSONObject parse(String jsonStr) {
        return JSONObject.parseObject(jsonStr);
    }
}
