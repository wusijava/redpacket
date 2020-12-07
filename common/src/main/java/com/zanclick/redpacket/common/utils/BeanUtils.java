package com.zanclick.redpacket.common.utils;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * @author duchong
 * @description java bean 操作工具类
 * @date 2020-6-23 11:42:20
 */
public class BeanUtils {

    /**
     * 将任意java对象转换成Map
     *
     * @param obj 对象
     * @return Map<String, Object>
     */
    public static Map<String, Object> toMap(Object obj) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
        PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
        for (PropertyDescriptor property : propertyDescriptors) {
            String key = property.getName();
            if (key.compareToIgnoreCase("class") == 0) {
                continue;
            }
            Method getter = property.getReadMethod();
            Object value = getter != null ? getter.invoke(obj) : null;
            if (value == null) {
                continue;
            }
            map.put(key, value);
        }
        return map;
    }


    /**
     * 讲对象转换成Map
     *
     * @param obj
     */
    public static Map<String, String> toStringMap(Object obj) throws IntrospectionException, InvocationTargetException, IllegalAccessException {
        Map<String, String> map = new HashMap<>();
        BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
        PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
        for (PropertyDescriptor property : propertyDescriptors) {
            String key = property.getName();
            if (key.compareToIgnoreCase("class") == 0) {
                continue;
            }
            Method getter = property.getReadMethod();
            Object value = getter != null ? getter.invoke(obj) : null;
            if (value == null) {
                continue;
            }
            map.put(key, String.valueOf(value));
        }
        return map;
    }
}
