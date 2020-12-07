package com.zanclick.redpacket.api.mapper;

import com.zanclick.redpacket.api.entity.NotifyMessage;
import com.zanclick.redpacket.common.base.dao.mybatis.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author long5
 * @date 2020-08-25 17:28:24
 **/
@Mapper
public interface NotifyMessageMapper extends BaseMapper<NotifyMessage,Long> {

}
