package com.zanclick.redpacket.sdk.response;

import com.zanclick.redpacket.sdk.ZcApiResult;
import com.zanclick.redpacket.sdk.model.EstablishRedPacketModel;

/**
 * @Author: huze
 * @Date: 2020/12/10 15:57
 */
public class EstablishRedPacketResponse extends ZcApiResult<EstablishRedPacketModel> {
    @Override
    public Class<EstablishRedPacketModel> modelClass() {
        return EstablishRedPacketModel.class;
    }

}
