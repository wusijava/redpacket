package com.zanclick.redpacket.user.mapper;

import com.zanclick.redpacket.common.base.dao.mybatis.BaseMapper;
import com.zanclick.redpacket.user.entity.Role;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author wusi
 * @date 2020-12-07 14:39:16
 **/
@Mapper
public interface RoleMapper extends BaseMapper<Role,Long> {


}
