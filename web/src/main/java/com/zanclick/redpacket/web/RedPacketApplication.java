package com.zanclick.redpacket.web;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.ComponentScan;

/**
 * @ Description   :  红包组件启动类
 * @ Author        :  wusi
 * @ CreateDate    :  2020/12/4$ 14:53$
 */
@ComponentScan(value = {"com.zanclick.redpacket"})
@MapperScan(value = {"com.zanclick.redpacket.*.mapper"})
@EnableConfigurationProperties
@SpringBootApplication
@ServletComponentScan({"com.zanclick.redpacket.user.filter"})
public class RedPacketApplication {
    public static void main(String[] args) {
        SpringApplication.run(RedPacketApplication.class, args);
    }
}
