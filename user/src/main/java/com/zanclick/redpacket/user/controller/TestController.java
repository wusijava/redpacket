package com.zanclick.redpacket.user.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ Description   :  java类作用描述
 * @ Author        :  wusi
 * @ CreateDate    :  2020/12/4$ 16:43$
 */
@RequestMapping("web/test")
@RestController
public class TestController {
    @RequestMapping("/zyjk")
    public void test(){
        System.out.println(123);
    }
}
