package com.zanclick.redpacket.user.service;

import com.zanclick.redpacket.api.anonation.OpenApi;
import com.zanclick.redpacket.api.anonation.OpenApiService;
import com.zanclick.redpacket.common.base.service.BaseService;
import com.zanclick.redpacket.common.swagger.annotation.OpenV1Api;
import com.zanclick.redpacket.sdk.enums.ApiMethod;
import com.zanclick.redpacket.user.entity.User;
import javafx.scene.chart.ValueAxis;
import org.springframework.stereotype.Service;

/**
 * @author long5
 * @date 2020-11-28 19:42:09
 **/
@OpenApiService
@Service
public interface UserService extends BaseService<User,Long> {

    User findByUsername(String username);

    @OpenApi(api = ApiMethod.RISK_MERCHANT_CHECK)
    void test();




}
