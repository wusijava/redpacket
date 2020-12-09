package com.zanclick.redpacket.core.mapper;

import com.zanclick.redpacket.common.base.dao.mybatis.BaseMapper;
import com.zanclick.redpacket.core.entity.Wallet;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author wusi
 * @date 2020-12-07 14:30:32
 **/
@Mapper
public interface WalletMapper extends BaseMapper<Wallet,Long> {

    Wallet findByUserName(String userName);

}
