package com.zanclick.redpacket.configuration.entity;

import com.zanclick.redpacket.common.model.Identifiable;
import lombok.Data;

/**
 * @author huze
 * @title: AliPayConfiguration
 * @projectName dz-riskgo
 * @description: TODO
 * @date 2020/9/1015:20
 */
@Data
public class AliPayConfiguration implements Identifiable<Long> {

    private Long id;

    private String appId;

    private String gateWay;

    /**
     * 私钥
     */
    private String privateKey;
    /**
     * 公钥
     */
    private String publicKey;

    private String format;
    private String charset;
    /**
     * 签名
     */
    private String signType;

    /**
     * 0关闭 1开启
     */
    private Integer state;




}
