package com.zanclick.redpacket.user.util;


import com.zanclick.redpacket.common.cache.RedisUtils;
import com.zanclick.redpacket.user.modal.LoginInfo;

import java.util.HashMap;
import java.util.Map;

/**
 * 登录信息管理工具类
 * */
public class LoginUtils {

    static String LOGIN_CACHE_MAP = "LOGIN_CACHE_MAP";

    static Long WEB_LOGIN_CACHE_TIME = 1000 * 60 * 60L;

    /**
     * 设置当前登录状态
     *
     * @param info
     */
    public static String setLoginCache(LoginInfo info) {
        Long time = -1L;
        if (LoginInfo.Type.WEB.getCode().equals(info.getType())) {
            time = WEB_LOGIN_CACHE_TIME;
        }
        String jwt = setLoginMap(info);
        String key = info.loginKey();
        RedisUtils.set(jwt, key, time);
        return jwt;
    }

    /**
     * 清除所有客户端的登录缓存
     *
     * @param uid      用户uid
     * @param username 用户登录名称
     */
    public static void clearAllLoginCache(String uid, String username) {
        LoginInfo info = new LoginInfo();
        info.setUsername(username);
        info.setUid(uid);
        String[] loginTypes = {LoginInfo.Type.H5.getCode(), LoginInfo.Type.WEB.getCode(), LoginInfo.Type.OPEN.getCode()};
        for (String type : loginTypes) {
            info.setType(type);
            clearLoginMap(info);
        }
    }

    /**
     * 获取指定的登录信息
     *
     * @param token 登录token
     * @return uid-username-type  登录缓存的key
     */
    public static String getLoginCache(String token) {
        Object object = RedisUtils.get(token);
        return object == null ? null : (String) object;
    }

    /**
     * 清除指定的登录信息
     *
     * @param token 登录token
     */
    public static void clearLoginCache(String token) {
        String key = getLoginCache(token);
        clearLoginMap(key);
    }

    static String setLoginMap(LoginInfo info) {
        String key = info.loginKey();
        Map<String, String> map = getLoginMap();
        String oldJwt = map.get(key);
        if (oldJwt != null) {
            return oldJwt;
        }
        map.put(key, info.getJwt());
        RedisUtils.set(LOGIN_CACHE_MAP, map);
        return info.getJwt();
    }

    static void clearLoginMap(String key) {
        Map<String, String> map = getLoginMap();
        String oldJwt = map.get(key);
        if (oldJwt != null) {
            RedisUtils.del(oldJwt);
        }
        RedisUtils.set(LOGIN_CACHE_MAP, map);
    }

    static void clearLoginMap(LoginInfo info) {
        if (info == null) {
            return;
        }
        String key = info.loginKey();
        clearLoginMap(key);
    }

    static Map<String, String> getLoginMap() {
        Object object = RedisUtils.get(LOGIN_CACHE_MAP);
        return object == null ? new HashMap<>() : (Map<String, String>) object;
    }
}
