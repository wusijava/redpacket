package com.zanclick.redpacket.common.utils;

import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;

/**
 * @author duchong
 * @description
 * @date 2019-10-15 14:09:54
 */
@Slf4j
public class MoneyUtils {

    /**
     * 金额相除
     *
     * @param money1
     * @param money2
     * @return
     */
    public static String divide(String money1, String money2) {
        BigDecimal total = new BigDecimal(money1).divide(new BigDecimal(money2),2, BigDecimal.ROUND_HALF_EVEN);
        return total.setScale(2, BigDecimal.ROUND_HALF_EVEN).toString();
    }

    /**
     * 金额相加
     *
     * @param money1
     * @param money2
     * @return
     */
    public static String add(String money1, String money2) {
        BigDecimal total = new BigDecimal(money1).add(new BigDecimal(money2));
        return total.setScale(2, BigDecimal.ROUND_HALF_EVEN).toString();
    }

    /**
     * 金额相减
     *
     * @param money1
     * @param money2
     * @return
     */
    public static String subtract(String money1, String money2) {
        BigDecimal total = new BigDecimal(money1).subtract(new BigDecimal(money2));
        return total.setScale(2, BigDecimal.ROUND_HALF_EVEN).toString();
    }

    /**
     * 金额相乘
     *
     * @param money1
     * @param money2
     * @return
     */
    public static String multiply(String money1, String money2) {
        BigDecimal total = new BigDecimal(money1).multiply(new BigDecimal(money2));
        return total.setScale(2, BigDecimal.ROUND_HALF_EVEN).toString();
    }

    /**
     * 检查金额
     *
     * @param money
     * @param money
     * @return
     */
    public static Boolean check(String money) {
        try {
            new BigDecimal(money);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    /**
     * 比较大小
     *
     * @param money1
     * @param money2
     * @return
     */
    public static Boolean large(String money1,String money2) {
        return new BigDecimal(money1).compareTo(new BigDecimal(money2)) == 1;
    }
    /**
     * 比较大小
     *
     * @param money1
     * @param money2
     * @return
     */
    public static Boolean largeOrEqual(String money1,String money2) {
        int i = new BigDecimal(money1).compareTo(new BigDecimal(money2));
        return i == 0 || i == 1;
    }

    /**
     * 比较大小
     *
     * @param money1
     * @param money2
     * @return
     */
    public static Boolean equal(String money1,String money2) {
        int i = new BigDecimal(money1).compareTo(new BigDecimal(money2));
        return i == 0;
    }

    /**
     * 格式化金额
     *
     * @param money
     * @param money
     * @return
     */
    public static String format(String money) {
        if (money == null){
            return "0.00";
        }
        try {
            return new BigDecimal(money).setScale(2, BigDecimal.ROUND_HALF_EVEN).toString();
        }catch (Exception e){
            log.error("金额格式化错误:{}",e);
        }
        return "0.00";
    }

    public static BigDecimal getFormatMoney(String total_money) {
        if (total_money == null) {
            return new BigDecimal("0.00");
        }
        BigDecimal decimal = new BigDecimal("0.00");
        try {
            decimal = decimal.add(new BigDecimal(total_money));
        } catch (Exception e) {
            return decimal;
        }
        return decimal.setScale(2, BigDecimal.ROUND_HALF_EVEN);
    }
    public static boolean largeMoney(String money1, String money2) {
        try {
            Integer result = getFormatMoney(money1).compareTo(getFormatMoney(money2));
            return result == 1;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

}
