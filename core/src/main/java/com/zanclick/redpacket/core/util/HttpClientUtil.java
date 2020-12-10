package com.zanclick.redpacket.core.util;


import com.alibaba.fastjson.JSON;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.util.EntityUtils;

import java.io.*;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: eddie
 * @Date: 2018-09-27
 */

public class HttpClientUtil {


    public static final String CHARSET_UTF_8 = "UTF-8";


    public static final String request_id = "request-id";

    public static final String dealer_id="dealer-id";

    /**
     * post请求
     * @param reqUrl
     * @param reqParams
     * @return
     */
    public static String doPost(String reqUrl, Map<String, String> reqParams) {
        if (reqParams == null) {
            reqParams = new HashMap<>();
        }
        CloseableHttpClient client = HttpClients.createDefault();
        HttpPost post = new HttpPost(reqUrl);
        RequestConfig requestConfig = RequestConfig.custom().
                setConnectTimeout(180 * 1000).setConnectionRequestTimeout(180 * 1000)
                .setSocketTimeout(180 * 1000).setRedirectsEnabled(true).build();
        post.setConfig(requestConfig);
        try {
            List<NameValuePair> params = new ArrayList<>();
            for (String key : reqParams.keySet()) {
                params.add(new BasicNameValuePair(key,String.valueOf(reqParams.get(key))));
            }
            UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(params, CHARSET_UTF_8);
            /* 设置参数 */
            post.setEntity(urlEncodedFormEntity);
            if (reqParams.get(request_id)!=null){
                post.setHeader(request_id, reqParams.get(request_id));
            }
            if (reqParams.get(dealer_id)!=null){
                post.setHeader(dealer_id, reqParams.get(dealer_id));
            }
            HttpResponse response = client.execute(post);
            HttpEntity entity = response.getEntity();
            String returnMsg = EntityUtils.toString(entity, CHARSET_UTF_8);
            return returnMsg;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            /* 释放链接 */
            post.releaseConnection();
        }
    }


    /**
     * 封装支付HTTP POST方法
     *
     * @param
     * @param
     * @return
     * @throws ClientProtocolException
     * @throws IOException
     */
    public static String postPayMsg(String url, Map<String, String> paramMap) throws ClientProtocolException, IOException {
        HttpClient httpClient = new DefaultHttpClient();
        httpClient.getParams().setIntParameter(CoreConnectionPNames.SO_TIMEOUT, 15000);
        httpClient.getParams().setIntParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 13000);
        HttpPost httpPost = new HttpPost(url);
        httpPost.setProtocolVersion(HttpVersion.HTTP_1_0);
        List<NameValuePair> formparams = setHttpParams(paramMap);
        UrlEncodedFormEntity param = new UrlEncodedFormEntity(formparams, CHARSET_UTF_8);
        //通过setEntity()设置参数给post
        httpPost.setEntity(param);
        if (paramMap.get(request_id)!=null){
            httpPost.setHeader(request_id, paramMap.get(request_id));
        }
        if (paramMap.get(dealer_id)!=null){
            httpPost.setHeader(dealer_id, paramMap.get(dealer_id));
        }
        // 利用httpClient的execute()方法发送请求并且获取返回参数
        HttpResponse response = httpClient.execute(httpPost);
        String httpEntityContent = getHttpEntityContent(response);
        httpPost.abort();
        return httpEntityContent;
    }

    /**
     * 设置请求参数
     *
     * @param
     * @return
     */
    private static List<NameValuePair> setHttpParams(Map<String, String> paramMap) {
        List<NameValuePair> formparams = new ArrayList<NameValuePair>();
        Set<Map.Entry<String, String>> set = paramMap.entrySet();
        for (Map.Entry<String, String> entry : set) {
            formparams.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
        }
        return formparams;
    }

    /**
     * 获得响应HTTP实体内容
     *
     * @param response
     * @return
     * @throws IOException
     * @throws UnsupportedEncodingException
     */
    private static String getHttpEntityContent(HttpResponse response) throws IOException, UnsupportedEncodingException {
        //通过HttpResponse 的getEntity()方法获取返回信息
        HttpEntity entity = response.getEntity();
        if (entity != null) {
            InputStream is = entity.getContent();
            BufferedReader br = new BufferedReader(new InputStreamReader(is, CHARSET_UTF_8));
            String line = br.readLine();
            StringBuilder sb = new StringBuilder();
            while (line != null) {
                sb.append(line + "\n");
                line = br.readLine();
            }
            br.close();
            is.close();
            return sb.toString();
        }
        return "";
    }

    public static String post(String reqUrl, Map<String, String> reqParams) throws Exception {
        if (reqParams == null) {
            reqParams = new HashMap<>();
        }
        System.out.println("---------请求地址：" + reqUrl);
        System.out.println("---------请求参数：" + JSON.toJSONString(reqParams));
        CloseableHttpClient client = HttpClients.createDefault();
        HttpPost post = new HttpPost(reqUrl);
        RequestConfig requestConfig = RequestConfig.custom().
                setConnectTimeout(1800 * 1000).setConnectionRequestTimeout(1800 * 1000)
                .setSocketTimeout(1800 * 1000).setRedirectsEnabled(true).build();
        post.setConfig(requestConfig);
        try {
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            for (String key : reqParams.keySet()) {
                params.add(new BasicNameValuePair(key, reqParams.get(key)));
            }
            UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(params, "UTF-8");
            /* 设置参数 */
            post.setEntity(urlEncodedFormEntity);
            if (reqParams.get(request_id)!=null){
                post.setHeader(request_id, reqParams.get(request_id));
            }
            if (reqParams.get(dealer_id)!=null){
                post.setHeader(dealer_id, reqParams.get(dealer_id));
            }
            HttpResponse response = client.execute(post);
            HttpEntity entity = response.getEntity();
            String returnMsg = EntityUtils.toString(entity, "UTF-8");
            return returnMsg;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            /* 释放链接 */
            post.releaseConnection();
        }
    }

}
