package com.zanclick.redpacket.user.mapper;

import com.zanclick.redpacket.common.base.dao.mybatis.BaseMapper;
import com.zanclick.redpacket.user.entity.Role;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author long5
 * @date 2020-11-28 19:45:15
 **/
@Mapper
public interface RoleMapper extends BaseMapper<Role,Long> {


}
