package com.zanclick.redpacket.common.swagger.annotation;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface WebV1Api {

    String desc() default "后台专用接口 1.0";
}
