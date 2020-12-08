package com.zanclick.redpacket.user.mapper;

import com.zanclick.redpacket.common.base.dao.mybatis.BaseMapper;
import com.zanclick.redpacket.user.entity.Role;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author 27720
 * @date 2020-12-07 17:56:10
 **/
@Mapper
public interface RoleMapper extends BaseMapper<Role,Long> {


}
