package com.zanclick.redpacket.user.util;


import com.zanclick.redpacket.user.enums.DataRoles;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 字符串工具类
 *
 * @author lvlu
 * @date 2018-01-15 10:13
 **/
public class NumberGeneratorUtils extends org.apache.commons.lang.StringUtils {

    static SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");

    /**
     * 获取订单号
     */
    public static String generatorUid() {
        return generator("U");
    }


    /**
     * 获取订单号
     */
    public static String generatorMerchantNo() {
        return generator("M");
    }


    /**
     * 获取订单号
     */
    public static String generatorStoreNo() {
        return generator("S");
    }


    /**
     * 获取订单号
     */
    public static String generatorCashierNo() {
        return generator("C");
    }

    /**
     * 获取订单号
     */
    public static String generatorAgentNo() {
        return generator("A");
    }


    /**
     * 获取订单号
     */
    public static String generatorProvinceNo() {
        return generator("P");
    }

    /**
     * 获取订单号
     */
    public static String generatorDistractNo() {
        return generator("D");
    }

    /**
     * 获取订单号
     */
    public static String generatorUserNo() {
        return generator("U");
    }

    /**
     * 获取订单号
     */
    public static String generatorCityNo() {
        return generator("M");
    }


    /**
     * 获取订单号
     */
  /*  public static String generator(DataRoles roles) {
        String defaultRoleCode = null;
        switch (roles){
            case USER:
                defaultRoleCode = generatorMerchantNo();
                break;
            case ADMIN:
                defaultRoleCode = generatorUserNo();
                break;
            default:
                break;
        }
        return defaultRoleCode;
    }*/

    /**
     * 创建指定位数的随机数
     *
     * @param numberFlag 是否为纯数字
     * @param length     随机字符串长度
     */
    public static String createRandom(boolean numberFlag, int length) {
        String retStr = "";
        String strTable = numberFlag ? "1234567890" : "1234567890abcdefghijkmnpqrstuvwxyz";
        int len = strTable.length();
        boolean bDone = true;

        do {
            retStr = "";
            int count = 0;

            for (int i = 0; i < length; ++i) {
                double dblR = Math.random() * (double) len;
                int intR = (int) Math.floor(dblR);
                char c = strTable.charAt(intR);
                if ('0' <= c && c <= '9') {
                    ++count;
                }

                retStr = retStr + strTable.charAt(intR);
            }

            if (count >= 2) {
                bDone = false;
            }
        } while (bDone);

        return retStr;
    }

    static String generator(String gen) {
        StringBuffer sb = new StringBuffer();
        sb.append(gen);
        sb.append(sdf.format(new Date()));
        sb.append(createRandom(true,6));
        return sb.toString();
    }

}
