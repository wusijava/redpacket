package com.zanclick.redpacket.user.mapper;

import com.zanclick.redpacket.common.base.dao.mybatis.BaseMapper;
import com.zanclick.redpacket.user.entity.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author admin
 * @date 2020-12-09 11:05:38
 **/
@Mapper
public interface UserMapper extends BaseMapper<User,Long> {


    User findByUsername(String username);


    String findPassword(Long id);
}
