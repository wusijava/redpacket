package com.zanclick.redpacket.common.enums;

/**
 * @author duchong
 * @date 2020-6-10 15:01:12
 */
public class StringFormat {

   /**
    * 电话号码
    * */
   public static final String PHONE_REGEX = "^((13[0-9])|(14[5,7,9])|(15([0-3]|[5-9]))|(166)|(17[0,1,3,5,6,7,8])|(18[0-9])|(19[8|9]))\\d{8}$";

   /**
    * 电话号码
    * 1开头的11位数字，不做号段限制
    */
   public static final String PHONE_REGEX_ALL = "^(1)\\d{10}$";

   /**
    * 只能是数字
    * */
   public static final String ONLY_NUMBER_REGEX = "^\\\\d+$";


   /**
    * 密码
    * **/
   public static final String PASSWORD_REGEX = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,12}$";

   /**
    * 只支持数字字母或者中文组合
    * **/
   public static final String CHINESE_OR_NUMBER_OR_LETTER_REGEX = "[a-zA-Z0-9\\u4E00-\\u9FA5]*";
}
