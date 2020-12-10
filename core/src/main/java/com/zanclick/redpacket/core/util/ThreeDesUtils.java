package com.zanclick.redpacket.core.util;

import com.alibaba.fastjson.JSON;
import com.zanclick.redpacket.core.lx.request.HbOrderRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Slf4j
public class ThreeDesUtils {


    public static String getHBData(String orderId, String mchCode, String name, String phone, String targetAccountNo, String fee, String mplOrdNo, String mplOrdDt,
                                   String addressCode, String serect, String jsonList) throws Exception {
        HbOrderRequest bankBO = new HbOrderRequest(orderId, mchCode, name, targetAccountNo, phone, fee, mplOrdNo, mplOrdDt, addressCode, jsonList);
        return getEnc64Str(JSON.toJSONString(bankBO), serect);
//        byte[] decrypt;
//        decrypt = tripleDesEncrypt(JSON.toJSONString(bankBO).getBytes(StandardCharsets.UTF_8),
//                serect.getBytes(StandardCharsets.UTF_8));
//        byte[] enc64 = Base64.encodeBase64(decrypt);
//        String enc64Str = new String(enc64);
//        return enc64Str;
    }

    private static String getEnc64Str(String jsonObject, String serect) throws Exception {
        byte[] decrypt;
        decrypt = tripleDesEncrypt(jsonObject.getBytes(StandardCharsets.UTF_8),
                serect.getBytes(StandardCharsets.UTF_8));
        byte[] enc64 = Base64.encodeBase64(decrypt);
        String enc64Str = new String(enc64);
        return enc64Str;
    }

    public static String getSign(String appKey, String data, String orderId, String time) {
        String encryptText = "data=".concat(data).concat("&mess=")
                .concat("mess"+orderId).concat("&timestamp=").concat(time)
                .concat("&key=").concat(appKey);
        log.info("encryptText为：{}", encryptText);
        return hmacSha256(encryptText, appKey);
    }

    private static String hmacSha256(String message, String secret) {
        String hash = "";
        try {
            Mac sha256Hmac = Mac.getInstance("HmacSHA256");
            SecretKeySpec secretKey = new SecretKeySpec(secret.getBytes(), "HmacSHA256");
            sha256Hmac.init(secretKey);
            byte[] bytes = sha256Hmac.doFinal(message.getBytes());
            hash = byteArrayToHexString(bytes);
        } catch (Exception e) {
            log.error("Error HmacSHA256 ,{}" + e);
        }
        return hash;
    }

    private static String byteArrayToHexString(byte[] b) {
        StringBuilder hs = new StringBuilder();
        String stmp;
        for (int n = 0; b != null && n < b.length; n++) {
            stmp = Integer.toHexString(b[n] & 0XFF);
            if (stmp.length() == 1) {
                hs.append('0');
            }
            hs.append(stmp);
        }
        return hs.toString().toLowerCase();
    }

    static byte[] tripleDesEncrypt(byte[] content, byte[] key) throws Exception {
        byte[] icv = new byte[8];
        System.arraycopy(key, 0, icv, 0, 8);
        return tripleDesEncrypt(content, key, icv);
    }

    private static byte[] tripleDesEncrypt(byte[] content, byte[] key, byte[] icv) throws Exception {
        final SecretKey secretKey = new SecretKeySpec(key, "DESede");
        final Cipher cipher = Cipher.getInstance("DESede/CBC/PKCS5Padding");
        final IvParameterSpec iv = new IvParameterSpec(icv);
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, iv);
        return cipher.doFinal(content);
    }


    /**
     * 获取精确到秒的时间戳
     *
     * @param date
     * @return
     */
    public static String getSecondTimestamp(Date date) {
        if (null == date) {
            return "0";
        }
        String timestamp = String.valueOf(date.getTime());
        int length = timestamp.length();
        if (length > 3) {
            return String.valueOf(timestamp.substring(0, length - 3));
        } else {
            return "0";
        }
    }

    public static void main(String[] args) {
        System.out.println(getSecondTimestamp(new Date()));
    }
}