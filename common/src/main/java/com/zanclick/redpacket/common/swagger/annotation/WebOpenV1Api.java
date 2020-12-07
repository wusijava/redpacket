package com.zanclick.redpacket.common.swagger.annotation;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface WebOpenV1Api {

    String desc() default "后台管理专用开放接口 1.0";
}
