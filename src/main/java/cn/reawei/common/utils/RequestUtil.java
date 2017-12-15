package cn.reawei.common.utils;

import cn.reawei.common.enums.HttpMethods;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.http.*;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.*;
import org.apache.http.entity.BufferedHttpEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 发送一个HTTP请求
 */
public class RequestUtil {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private static final Integer INDEX = 0;

    /**
     * 根据请求的方式返回不同的请求
     *
     * @param params  参数
     * @param url     地址
     * @param method  请求方式
     * @param charset 编码格式
     * @throws Exception
     */
    private static HttpRequestBase getRequestParams(Map<String, String> params, String url, HttpMethods method, String charset) throws Exception {
        List<NameValuePair> paramList = new ArrayList<>();
        UrlEncodedFormEntity entity;
        switch (method.getMethod()) {
            case "GET":
                return new HttpGet(url + "?" + paramsToString(params, charset));
            case "POST":
                HttpPost httpPost = new HttpPost(url);
                for (String key : params.keySet()) {
                    paramList.add(new BasicNameValuePair(key, params.get(key)));
                }
                entity = new UrlEncodedFormEntity(paramList, charset);
                httpPost.setEntity(entity);
                return httpPost;
            case "PUT":
                HttpPut httpPut = new HttpPut(url);
                for (String key : params.keySet()) {
                    paramList.add(new BasicNameValuePair(key, params.get(key)));
                }
                entity = new UrlEncodedFormEntity(paramList, charset);
                httpPut.setEntity(entity);
                return httpPut;
            case "DELETE":
                return new HttpDelete(url);
        }
        return null;
    }

    /**
     * 发送Http请求
     *
     * @param url     请求地址
     * @param params  请求参数
     * @param charset 编码格式
     * @param methods 请求方式
     * @return 请求返回Response
     */
    public static String requestSend(String url, Map<String, String> params, String charset, HttpMethods methods) {
        CloseableHttpClient client = HttpClientBuilder.create().build();
        CloseableHttpResponse response;
        if (Objects.isNull(charset)) {
            charset = "utf-8";
        }
        try {
            HttpRequestBase request = getRequestParams(params, url, methods, charset);
            response = client.execute(request);
            Integer statusCode = response.getStatusLine().getStatusCode();
            response.close();
            if (Objects.equals(statusCode, HttpStatus.SC_OK)) {
                return getResponseBodyToString(response);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                client.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * get请求转换请求参数
     *
     * @param params  参数Map
     * @param charset 编码格式
     * @return 转换后的字符串
     */
    private static String paramsToString(Map<String, String> params, String charset) {
        if (params == null || params.size() == 0) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        try {
            int i = 0;
            for (String key : params.keySet()) {
                if (Objects.equals(i, INDEX)) {
                    sb.append(key + "=" + URLEncoder.encode(params.get(key), charset));
                } else {
                    sb.append("&" + key + "=" + URLEncoder.encode(params.get(key), charset));
                }
                i++;
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    /**
     * 获取Response 返回的内容
     *
     * @param response response
     * @return 内容字符串
     * @throws Exception 异常
     */
    private static String getResponseBodyToString(HttpResponse response) throws Exception {
        StringBuilder sb = new StringBuilder();
        HttpEntity httpEntity = response.getEntity();
        if (httpEntity != null) {
            httpEntity = new BufferedHttpEntity(httpEntity);
            InputStream is = httpEntity.getContent();
            BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            String str;
            while ((str = br.readLine()) != null) {
                sb.append(str);
            }
            is.close();
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        System.out.println(requestSend("http://api.reawei.cn/api/v1/user", null, "utf-8", HttpMethods.GET));
    }
}
