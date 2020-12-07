package com.zanclick.redpacket.web.X;

import com.zanclick.redpacket.configuration.service.AppService;
import com.zanclick.redpacket.user.entity.User;
import com.zanclick.redpacket.user.service.UserService;
import com.zanclick.redpacket.web.RedPacketApplication;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @ Description   :  java类作用描述
 * @ Author        :  wusi
 * @ CreateDate    :  2020/12/4$ 17:29$
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RedPacketApplication.class)
public class Test {

    @Autowired
    private AppService appService;
    @Autowired
    private UserService userService;

    @org.junit.Test
    public  void test() {
        User admin = userService.findByUsername("admin");
    }
}
