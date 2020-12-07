package com.zanclick.redpacket.user.service.impl;

import com.zanclick.redpacket.api.anonation.OpenApiService;
import com.zanclick.redpacket.common.base.dao.mybatis.BaseMapper;
import com.zanclick.redpacket.common.base.service.impl.BaseMybatisServiceImpl;
import com.zanclick.redpacket.user.entity.User;
import com.zanclick.redpacket.user.mapper.UserMapper;
import com.zanclick.redpacket.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author long5
 * @date 2020-11-28 19:42:09
 **/
@Service
@OpenApiService
public class UserServiceImpl extends BaseMybatisServiceImpl<User,Long> implements UserService {

    @Autowired
    private UserMapper userMapper;


    @Override
    protected BaseMapper<User, Long> getBaseMapper() {
        return userMapper;
    }

    @Override
    public User findByUsername(String username) {
        return userMapper.findByUsername(username);
    }

    @Override
    public void test(User user) {
        System.out.println(132);
    }
}
