package com.zanclick.redpacket.common.sms;


import com.zanclick.redpacket.common.cache.RedisUtils;

/**
 * 所有发送短信的地方，发送内容保存时间全部存在这
 * @author duchong
 * @date 2019-10-25 15:01:44
 */
public class SendSms {

    static String REFUND_KEY = "Refund:";
    static Long REFUND_CACHE_TIME = 120L;

    static String LOGIN_KEY = "Login:";
    static Long LOGIN_CACHE_TIME = 120L;

    static String REGISTER_KEY = "Register:";
    static Long REGISTER_CACHE_TIME = 120L;

    static String FORGET_PASSWORD_KEY = "ForgetPassword:";
    static Long FORGET_PASSWORD_TIME = 120L;

    static String CHANGE_MOBILE_KEY = "ChangeMobile:";
    static Long CHANGE_MOBILE_TIME = 120L;

    static String BINDING_MOBILE_KEY = "BindingMobile:";
    static Long BINDING_MOBILE_TIME = 120L;

    static String VISIT_PASSWORD_KEY = "VisitPassword:";
    static Long VISIT_PASSWORD_TIME = 120L;

    static String CHANGE_REFUND_PASSWORD_KEY = "ChangeRefundPassword:";
    static Long CHANGE_REFUND_TIME = 120L;
    /**
     * 推送退款验证码
     *
     * @param mobile
     * @return
     */
    public static Boolean sendRefundSms(String mobile) {
        String code = createSmsCode();
        String content = "您正在进行退款操作，验证码:"+code;
        Boolean flag = SmsUtils.sendSms(content,mobile);
        if (flag) {
            setSmsCode(REFUND_KEY,REFUND_CACHE_TIME,mobile, code);
        }
        return flag;
    }

    /**
     * 获取退款验证码
     *
     * @param mobile
     * @return
     */
    public static String getRefundSms(String mobile) {
        return getSmsCode(REFUND_KEY,mobile);
    }


    /**
     * 验证退款验证
     *
     * @param mobile
     * @return
     */
    public static Boolean verifyRefundSms(String mobile, String code) {
        return verifySms(REFUND_KEY,mobile,code);
    }


    /**
     * 推送登录验证码
     *
     * @param mobile
     * @return
     */
    public static Boolean sendLoginSms(String mobile) {
        String code = createSmsCode();
        String content = "您正在进行登录，验证码："+code;
        Boolean flag = SmsUtils.sendSms(content,mobile);
        if (flag) {
            setSmsCode(LOGIN_KEY,LOGIN_CACHE_TIME,mobile, code);
        }
        return flag;
    }

    /**
     * 获取登录验证码
     *
     * @param mobile
     * @return
     */
    public static String getLoginSms(String mobile) {
        return getSmsCode(LOGIN_KEY,mobile);
    }


    /**
     * 登录验证
     *
     * @param mobile
     * @return
     */
    public static Boolean verifyLoginSms(String mobile, String code) {
        return verifySms(LOGIN_KEY,mobile,code);
    }


    /**
     * 推送注册短信验证码
     *
     * @param mobile
     * @return
     */
    public static Boolean sendRegisterSms(String mobile) {
        String code = createSmsCode();
        String content = "您正在进行注册操作，验证码:"+code;
        Boolean flag = SmsUtils.sendSms(content,mobile);
        if (flag) {
            setSmsCode(REGISTER_KEY,REGISTER_CACHE_TIME,mobile, code);
        }
        return flag;
    }

    /**
     * 推送注册短信验证码
     *
     * @param mobile
     * @param code
     * @return
     */
    public static Boolean verifyRegisterSms(String mobile, String code) {
        return verifySms(REGISTER_KEY,mobile,code);
    }

    /**
     * 获取注册短信验证码
     *
     * @param mobile
     * @return
     */
    public static String getRegisterSms(String mobile) {
        return getSmsCode(REGISTER_KEY,mobile);
    }


    /**
     * 推送找回密码短信验证码
     *
     * @param mobile
     * @return
     */
    public static Boolean sendForgetPasswordSms(String mobile) {
        String code = createSmsCode();
        String content = "您正在进行密码找回操作，验证码:"+code;
        Boolean flag = SmsUtils.sendSms(content,mobile);
        if (flag) {
            setSmsCode(FORGET_PASSWORD_KEY,FORGET_PASSWORD_TIME,mobile, code);
        }
        return flag;
    }

    /**
     * 推送找回密码短信验证码
     *
     * @param mobile
     * @param code
     * @return
     */
    public static Boolean verifyForgetPasswordSms(String mobile, String code) {
        return verifySms(FORGET_PASSWORD_KEY,mobile,code);
    }

    /**
     * 获取找回密码短信验证码
     *
     * @param mobile
     * @return
     */
    public static String getForgetPasswordSms(String mobile) {
        return getSmsCode(FORGET_PASSWORD_KEY,mobile);
    }

    /**
     * 推送修改退款密码短信验证码
     *
     * @param mobile
     * @return
     */
    public static Boolean sendChangeRefundPasswordSms(String mobile) {
        String code = createSmsCode();
        String content = "您正在修改退款密码，验证码:"+code;
        Boolean flag = SmsUtils.sendSms(content,mobile);
        if (flag) {
            setSmsCode(CHANGE_REFUND_PASSWORD_KEY,CHANGE_REFUND_TIME,mobile, code);
        }
        return flag;
    }

    /**
     * 推送修改退款密码短信验证码
     *
     * @param mobile
     * @param code
     * @return
     */
    public static Boolean verifyChangeRefundPasswordSms(String mobile, String code) {
        return verifySms(CHANGE_REFUND_PASSWORD_KEY,mobile,code);
    }

    /**
     * 获取修改退款密码短信验证码
     *
     * @param mobile
     * @return
     */
    public static String getChangeRefundPasswordSms(String mobile) {
        return getSmsCode(CHANGE_REFUND_PASSWORD_KEY,mobile);
    }

    /**
     * 推送修改手机号码短信验证码
     *
     * @param mobile
     * @return
     */
    public static Boolean sendChangeMobileSms(String mobile) {
        String code = createSmsCode();
        String content = "您正在修改手机号码，验证码:"+code;
        Boolean flag = SmsUtils.sendSms(content,mobile);
        if (flag) {
            setSmsCode(CHANGE_MOBILE_KEY,CHANGE_MOBILE_TIME,mobile, code);
        }
        return flag;
    }

    /**
     * 推送修改手机号码短信验证码
     *
     * @param mobile
     * @param code
     * @return
     */
    public static Boolean verifyChangeMobileSms(String mobile, String code) {
        return verifySms(CHANGE_MOBILE_KEY,mobile,code);
    }

    /**
     * 获取修改手机号码短信验证码
     *
     * @param mobile
     * @return
     */
    public static String getChangeMobileSms(String mobile) {
        return getSmsCode(CHANGE_MOBILE_KEY,mobile);
    }



    /**
     * 推送绑定新手机号码短信验证码
     *
     * @param mobile
     * @return
     */
    public static Boolean sendBindingMobileSms(String mobile) {
        String code = createSmsCode();
        String content = "您正在绑定手机号码，验证码:"+code;
        Boolean flag = SmsUtils.sendSms(content,mobile);
        if (flag) {
            setSmsCode(BINDING_MOBILE_KEY,BINDING_MOBILE_TIME,mobile, code);
        }
        return flag;
    }

    /**
     * 推送绑定新手机号码短信验证码
     *
     * @param mobile
     * @param code
     * @return
     */
    public static Boolean verifyBindingMobileSms(String mobile, String code) {
        return verifySms(BINDING_MOBILE_KEY,mobile,code);
    }

    /**
     * 获取绑定新手机号码短信验证码
     *
     * @param mobile
     * @return
     */
    public static String getBindingMobileSms(String mobile) {
        return getSmsCode(BINDING_MOBILE_KEY,mobile);
    }


    /**
     * 推送修改账目访问密码短信验证码
     *
     * @param mobile
     * @return
     */
    public static Boolean sendVisitPasswordSms(String mobile) {
        String code = createSmsCode();
        String content = "您正在修改账目访问密码，验证码:"+code;
        Boolean flag = SmsUtils.sendSms(content,mobile);
        if (flag) {
            setSmsCode(VISIT_PASSWORD_KEY,VISIT_PASSWORD_TIME,mobile, code);
        }
        return flag;
    }

    /**
     * 推送修改账目访问密码短信验证码
     *
     * @param mobile
     * @param code
     * @return
     */
    public static Boolean verifyVisitPasswordSms(String mobile, String code) {
        return verifySms(VISIT_PASSWORD_KEY,mobile,code);
    }

    /**
     * 获取修改账目访问密码短信验证码
     *
     * @param mobile
     * @return
     */
    public static String getVisitPasswordSms(String mobile) {
        return getSmsCode(VISIT_PASSWORD_KEY,mobile);
    }


    /**
     * 推送商户签约成功信息
     *
     * @param mobile
     * @param name
     * @return
     */
    public static Boolean sendMerchantRegisterSuccessSms(String mobile, String name) {
        String content = "您的资质（"+name+"）已通过审核，可使用二维码进行收款";
        Boolean flag = SmsUtils.sendSms(content,mobile);
        return flag;
    }

    /**
     * 推送商户签约失败信息
     *
     * @param mobile
     * @param name
     * @return
     */
    public static Boolean sendMerchantRegisterFailSms(String mobile, String name, String reason) {
        String content = "您的资质（"+name+"）已被驳回，原因:"+reason;
        Boolean flag = SmsUtils.sendSms(content,mobile);
        return flag;
    }



    /**
     * 验证短信验证码
     *
     * @param key 唯一键
     * @param mobile 手机号
     * @param code 验证码
     * @return
     */
    private static Boolean verifySms(String key, String mobile, String code) {
        Boolean flag = true;
        String oldCode = getSmsCode(key,mobile);
        if (oldCode == null) {
            flag = false;
        } else {
            flag = oldCode.equals(code);
        }
        return flag;
    }

    /**
     * 设置缓存验证码
     *
     * @param key 唯一键
     * @param time 缓存时间
     * @param mobile 手机号
     * @param code 验证码
     */
    private static void setSmsCode(String key, Long time, String mobile, String code) {
        RedisUtils.set(key + mobile, code, time);
    }

    /**
     * 获取验证码
     *
     * @param mobile 手机号
     * @param key 唯一键
     * @return 验证码
     */
    private static String getSmsCode(String key, String mobile) {
        Object object = RedisUtils.get(key + mobile);
        return object == null ? null : (String) object;
    }


    /**
     * 清除魂村的验证码
     *
     * @param key
     * @param mobile
     */
    private static void clearSmsCode(String key, String mobile) {
        RedisUtils.del(key + mobile);
    }

    /**
     * 创建短信验证码
     *
     * @return 短信验证码
     */
    private static String createSmsCode() {
        //TODO 上线需打开注释
        //      return StringUtils.createRandom(true, 6);
           return "666666";
    }
}
