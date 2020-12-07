package com.zanclick.redpacket.executor.anonation;

import com.zanclick.redpacket.executor.config.ExecutorConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @author zanclick
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import({ExecutorConfiguration.class})
@Documented
public @interface EnableExecutorConfiguration {
}
