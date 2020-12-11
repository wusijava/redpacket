package com.zanclick.redpacket.core.api;

import com.zanclick.redpacket.api.anonation.OpenApi;
import com.zanclick.redpacket.core.entity.RedPacket;
import com.zanclick.redpacket.sdk.enums.ApiMethod;
import com.zanclick.redpacket.sdk.model.*;

/**
 * @Author: huze 对外接口
 * @Date: 2020/12/10 16:02
 */
public interface ApiRedPacketService {

    /**
     * 创建红包
     */
    @OpenApi(api = ApiMethod.ESTABLISH_REDPACKET)
    EstablishRedPacketResultModel createRedPacket(EstablishRedPacketModel establishRedPacketModel);

    /**
     * 查询红包
     */
    @OpenApi(api = ApiMethod.QUERY_REDPACKET)
    QueryRedPacketResultModel queryRedPacket(QueryRedPacketModel queryRedPacketModel);

    /**
     * 查询失效
     */
    @OpenApi(api = ApiMethod.INVALID_REDPACKET)
    InvalidRedPacketResultModel invalidRedPacket(InvalidRedPacketModel invalidRedPacketModel);
}
