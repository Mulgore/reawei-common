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

public class RequestUtil {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private static HttpClient clientHTTP;

    public String send(String url, HttpMethods httpMethod, Map<String, String> params,
                       Header[] headers, String encoding) throws Exception {
        String body;
        try {
            //创建请求对象
            HttpRequestBase request = getRequest(url, httpMethod);
            //设置header信息
            request.setHeaders(headers);
            //判断是否支持设置entity(仅HttpPost、HttpPut、HttpPatch支持)
            if (HttpEntityEnclosingRequestBase.class.isAssignableFrom(request.getClass())) {
                //装填参数
                if (params != null) {
                    List<NameValuePair> paramList = new ArrayList<>();
                    for (String key : params.keySet()) {
                        paramList.add(new BasicNameValuePair(key, params.get(key)));
                    }
                    //设置参数到请求对象中
                    ((HttpEntityEnclosingRequestBase) request).setEntity(new UrlEncodedFormEntity(paramList, encoding));
                }
            } else {
                int idx = url.indexOf("?");
                logger.debug("请求地址：" + url.substring(0, (idx > 0 ? idx - 1 : url.length() - 1)));
                if (idx > 0) {
                    logger.debug("请求参数：" + url.substring(idx + 1));
                }
            }
            //调用发送请求
        } catch (UnsupportedEncodingException e) {
            throw new Exception(e);
        }
        return null;
    }

    private static HttpRequestBase getRequest(String url, HttpMethods method) {
        HttpRequestBase request;
        switch (method.getMethod()) {
            case "GET":
                request = new HttpGet(url);
                break;
            case "POST":
                request = new HttpPost(url);
                break;
            case "HEAD":
                request = new HttpHead(url);
                break;
            case "PUT":
                request = new HttpPut(url);
                break;
            case "DELETE":
                request = new HttpDelete(url);
                break;
            case "TRACE":
                request = new HttpTrace(url);
                break;
            case "PATCH":
                request = new HttpPatch(url);
                break;
            case "OPTIONS":
                request = new HttpOptions(url);
                break;
            default:
                request = new HttpPost(url);
                break;
        }
        return request;
    }

    private static HttpRequestBase getRequestParams(Map<String, String> params, String url, HttpMethods method, String charset) throws Exception {
        List<NameValuePair> paramList = new ArrayList<>();
        UrlEncodedFormEntity entity;
        switch (method.getMethod()) {
            case "GET":
                HttpGet httpGet = new HttpGet(url + "?" + paramsToString(params, charset));
                return httpGet;
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
                HttpDelete httpDelete = new HttpDelete(url);
                return httpDelete;
        }
        return null;
    }

    public static String requestSend(String url, Map<String, String> params, String charset, HttpMethods methods) {
        CloseableHttpClient client = HttpClientBuilder.create().build();
        CloseableHttpResponse response;
        String body = null;
        if (Objects.isNull(charset)) {
            charset = "utf-8";
        }
        try {
            HttpRequestBase request = getRequestParams(params, url, methods, charset);
            response = client.execute(request);
            Integer statusCode = response.getStatusLine().getStatusCode();
            response.close();
            if (!Objects.equals(statusCode, HttpStatus.SC_OK)) {
                return null;
            }
            body = getResponseBodyToString(response);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                client.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return body;
    }

    private static String paramsToString(Map<String, String> params, String charset) {
        if (params == null || params.size() == 0) {
            return null;
        }
        StringBuffer sb = new StringBuffer();
        try {
            int i = 0;
            for (String key : params.keySet()) {
                if (i == 0) {
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
        System.out.println(requestSend("https://www.baidu.com", null, "utf-8", HttpMethods.GET));
    }
}
