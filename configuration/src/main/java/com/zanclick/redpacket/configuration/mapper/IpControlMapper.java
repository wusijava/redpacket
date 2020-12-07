package com.zanclick.redpacket.configuration.mapper;


import com.zanclick.redpacket.common.base.dao.mybatis.BaseMapper;
import com.zanclick.redpacket.configuration.entity.IpControl;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author long5
 * @date 2020-08-21 22:26:58
 **/
@Mapper
public interface IpControlMapper extends BaseMapper<IpControl,Long> {


}
