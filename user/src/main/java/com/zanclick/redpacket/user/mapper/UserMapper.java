package com.zanclick.redpacket.user.mapper;

import com.zanclick.redpacket.common.base.dao.mybatis.BaseMapper;
import com.zanclick.redpacket.user.entity.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author long5
 * @date 2020-11-28 19:42:09
 **/
@Mapper
public interface UserMapper extends BaseMapper<User,Long> {


    User findByUsername(String username);


    String findPassword(Long id);
}
