package com.zanclick.redpacket.common.ratelimit.aop;


import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.util.concurrent.RateLimiter;
import com.zanclick.redpacket.common.ratelimit.anonation.RateLimit;
import com.zanclick.redpacket.common.utils.IpUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;


/**
 * @author zanclick
 */
@Component
@Aspect
public class LimitAspect {

    private static Cache<String, RateLimiter> cache = CacheBuilder.newBuilder().expireAfterAccess(1200, TimeUnit.SECONDS).build();

    @Pointcut("@annotation(com.zanclick.redpacket.common.ratelimit.anonation.RateLimit)")
    public void ServiceAspect() {

    }

    @Around("ServiceAspect()")
    public Object around(ProceedingJoinPoint joinPoint) {
        Signature signature = joinPoint.getSignature();
        RateLimit rateLimit = ((MethodSignature) signature).getMethod().getAnnotation(RateLimit.class);
        if (rateLimit != null) {
            String key = signature.getName();
            if (rateLimit.ipLimit() && getIp() != null) {
                key += getIp();
            }
            RateLimiter rateLimiter = null;
            try {
                rateLimiter = cache.get(key, () -> RateLimiter.create(rateLimit.permitsPerSecond()));
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
            Boolean flag = rateLimiter.tryAcquire();
            Object obj = null;
            try {
                if (flag) {
                    obj = joinPoint.proceed();
                } else {
                    return null;
                }
            } catch (Throwable e) {
                e.printStackTrace();
            }
            return obj;
        } else {
            return null;
        }
    }

    private String getIp() {
        try {
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            return IpUtils.getIpAddress(request);
        } catch (Exception e) {
            return null;
        }

    }
}
