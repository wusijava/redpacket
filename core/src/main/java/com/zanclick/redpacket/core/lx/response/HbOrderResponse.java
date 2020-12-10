package com.zanclick.redpacket.core.lx.response;

import lombok.Data;

/**
 * @Author: mapei
 * @Date: 2020/9/25 14:59
 * 和包支付实时下单返回
 */
@Data
public class HbOrderResponse {

    private String code;

    private String message;

    private String data;

    private String request_id;

    @Data
    public static  class Detail {
        private String pay;
        private String ref;
        private String orderId;
    }

    public Boolean isSuccess() {
        return "0000".equals(code);
    }


}
