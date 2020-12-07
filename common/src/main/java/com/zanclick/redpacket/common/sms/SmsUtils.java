package com.zanclick.redpacket.common.sms;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zanclick.redpacket.common.utils.DataUtils;
import com.zanclick.redpacket.common.utils.StringUtils;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import static io.jsonwebtoken.impl.TextCodec.BASE64;

/**
 * Created by LYQ on 2017/10/10 0010.
 * 发送短信验证码
 */
public class SmsUtils {
    private static Logger log = LoggerFactory.getLogger(SmsUtils.class);
    private static final String URL = "http://211.147.239.62:9051/api/v1.0.0/message/mass/send";
    private static final String AUTHORIZATION = "aHl6aDNAaHl6aDM6YjJiNjFiNDBlYjIwZmMwZjRiNWQwNDNlNDdhN2ZlMWQ=";

    public static boolean sendSms(String content, String mobile) {
        try {
            if (DataUtils.isNotEmpty(mobile) && DataUtils.isNotEmpty(content)) {
                //TODO 防止短信验证码推到线上去
                if (1 == 1) {
                    System.err.println("----------------------------------");
                    System.err.println("推送号码:" + mobile);
                    System.err.println("推送内容:" + content);
                    System.err.println("----------------------------------");
                    return true;
                }
                HttpURLConnection conn = (HttpURLConnection) new URL(URL).openConnection();
                conn.setDoOutput(true);
                conn.setDoInput(true);
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "application/json;charset=utf-8");
                conn.setRequestProperty("Accept", "application/json");
                conn.setRequestProperty("Authorization", AUTHORIZATION);
                conn.connect();

                JSONObject json = new JSONObject();
                json.put("batchName", System.currentTimeMillis() + StringUtils.createRandom(true, 8));
                json.put("content", content);
                json.put("msgType", "com/leguo/jd/common/sms");
                json.put("bizType", "100");
                JSONArray items = new JSONArray();
                JSONObject item = new JSONObject();
                item.put("to", mobile);
                item.put("customMsgID", "");
                items.add(item);
                json.put("items", items);

                OutputStreamWriter out = new OutputStreamWriter(conn.getOutputStream());
                out.write(json.toJSONString());
                out.close();

                StringBuilder response = new StringBuilder();
                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String tmp = null;
                log.info("********** Response **********");
                while ((tmp = reader.readLine()) != null) {
                    response.append(tmp);
                }
                if (DataUtils.isNotEmpty(response.toString())) {
                    JSONObject result = JSONObject.parseObject(response.toString());
                    if (result.containsKey("code") && "0".equals(result.get("code"))) {
                        return true;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    private static String generateAuthorization(String username, String password) {
        String md5Pwd = DigestUtils.md5Hex(password);
        String pair = username + ":" + md5Pwd;
        return BASE64.encode(pair.getBytes());
    }

    public static void main(String[] args) {
        sendSms("17786111105","您的短信验证码为：6666666");
    }

}
