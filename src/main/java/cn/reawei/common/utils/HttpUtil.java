package cn.reawei.common.utils;

import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.*;

/**
 * Created in 2018/5/11 19:46
 *
 * @author qigong
 */
public class HttpUtil {

    /**
     * 设置配置请求参数
     */
    private static int defaultSocketTimeout = 60000;
    /**
     * 请求超时时间
     */
    private static int defaultRequestTimeout = 35000;
    /**
     * 连接主机服务超时时间
     */
    private static int defaultIdleConnTimeout = 35000;

    private static final String CONTENT_TYPE = "Content-Type";
    /**
     * 配置请求参数实例
     */
    private static RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(defaultIdleConnTimeout).setConnectionRequestTimeout(defaultRequestTimeout).setSocketTimeout(defaultSocketTimeout).build();

    public static String doGet(String url) {
        Map<String, String> headers = new HashMap<>(16);
        headers.put("Content-Type", "application/json; charset=utf-8");
        headers.put("Accept", "text/plain;charset=utf-8");
        return httpGet(url, "utf-8", headers, null);
    }

    public static String doGet(String url, Map<String, Object> params) {
        Map<String, String> headers = new HashMap<>(16);
        headers.put("Content-Type", "application/json; charset=utf-8");
        headers.put("Accept", "text/plain;charset=utf-8");
        return httpGet(url, "utf-8", headers, params);
    }

    public static String doGet(String url, Map<String, Object> params, Map<String, String> headers, String charset) {
        String defaultCharset = "utf-8";
        if (Objects.nonNull(charset) && charset.length() > 0) {
            defaultCharset = charset;
        }
        if (StringUtil.isBlank(headers.get(CONTENT_TYPE))) {
            headers.put("Content-Type", "application/json; charset=" + defaultCharset);
        }
        headers.put("Accept", "text/plain;charset=" + defaultCharset);
        return httpGet(url, defaultCharset, headers, params);
    }

    public static String doPost(String url) {
        Map<String, String> headers = new HashMap<>(16);
        headers.put("Content-Type", "application/x-www-form-urlencoded");
        return httpPost(url, "utf-8", headers, null);
    }

    public static String doPost(String url, Map<String, Object> params) {
        Map<String, String> headers = new HashMap<>(16);
        headers.put("Content-Type", "application/x-www-form-urlencoded");
        return httpPost(url, "utf-8", headers, params);
    }

    public static String doPost(String url, Map<String, Object> params, Map<String, String> headers, String charset) {
        String defaultCharset = "utf-8";
        if (Objects.nonNull(charset) && charset.length() > 0) {
            defaultCharset = charset;
        }
        if (StringUtil.isBlank(headers.get(CONTENT_TYPE))) {
            headers.put("Content-Type", "application/x-www-form-urlencoded");
        }
        return httpPost(url, defaultCharset, headers, params);
    }

    private static String httpGet(String url, String charset, Map<String, String> headers, Map<String, Object> params) {
        CloseableHttpClient httpClient = null;
        CloseableHttpResponse response = null;
        String result = "";
        try {
            // 通过址默认配置创建一个httpClient实例
            httpClient = HttpClients.createDefault();
            // 创建httpGet远程连接实例
            HttpGet httpGet = new HttpGet(url + mapToQueryString(params, charset));
            // 设置请求头信息，鉴权
            setHeaders(headers, null, httpGet);
            // 为httpGet实例设置配置
            httpGet.setConfig(requestConfig);
            // 执行get请求得到返回对象
            response = httpClient.execute(httpGet);
            // 通过返回对象获取返回数据
            HttpEntity entity = response.getEntity();
            // 通过EntityUtils中的toString方法将结果转换为字符串
            result = EntityUtils.toString(entity);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // 关闭资源
            if (null != response) {
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (null != httpClient) {
                try {
                    httpClient.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }


    private static String httpPost(String url, String charset, Map<String, String> headers, Map<String, Object> params) {
        CloseableHttpClient httpClient;
        CloseableHttpResponse httpResponse = null;
        String result = "";
        // 创建httpClient实例
        httpClient = HttpClients.createDefault();
        // 创建httpPost远程连接实例
        HttpPost httpPost = new HttpPost(url);
        // 为httpPost实例设置配置
        httpPost.setConfig(requestConfig);
        setHeaders(headers, httpPost, null);
        // 封装post请求参数
        mapToPostParams(params, charset, httpPost);
        try {
            // httpClient对象执行post请求,并返回响应参数对象
            httpResponse = httpClient.execute(httpPost);
            // 从响应对象中获取响应内容
            HttpEntity entity = httpResponse.getEntity();
            result = EntityUtils.toString(entity);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // 关闭资源
            if (null != httpResponse) {
                try {
                    httpResponse.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (null != httpClient) {
                try {
                    httpClient.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }

    /**
     * 设置GET请求参数
     *
     * @param params  参数
     * @param charset 编码格式
     */
    static String mapToQueryString(Map<String, Object> params, String charset) {
        if (Objects.isNull(params) || params.isEmpty()) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        try {
            int i = 0;
            for (String key : params.keySet()) {
                if (i == 0) {
                    sb.append("?").append(key).append("=").append(URLEncoder.encode(params.get(key).toString(), charset));
                } else {
                    sb.append("&").append(key).append("=").append(URLEncoder.encode(params.get(key).toString(), charset));
                }
                i++;
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    /**
     * 设置POST请求参数
     *
     * @param params   参数
     * @param charset  编码格式
     * @param httpPost 请求客户端
     */
    static void mapToPostParams(Map<String, Object> params, String charset, HttpPost httpPost) {
        if (Objects.isNull(params) || params.isEmpty()) {
            return;
        }
        List<NameValuePair> param = new ArrayList<>();
        // 通过map集成entrySet方法获取entity
        Set<Map.Entry<String, Object>> entrySet = params.entrySet();
        // 循环遍历，获取迭代器
        for (Map.Entry<String, Object> mapEntry : entrySet) {
            param.add(new BasicNameValuePair(mapEntry.getKey(), mapEntry.getValue().toString()));
        }
        // 为httpPost设置封装好的请求参数
        try {
            httpPost.setEntity(new UrlEncodedFormEntity(param, charset));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    /**
     * 设置请求头
     *
     * @param headers  请求头参数
     * @param httpPost 请求客户端
     * @param httpGet  请求客户端
     */
    static void setHeaders(Map<String, String> headers, HttpPost httpPost, HttpGet httpGet) {
        for (String headerName : headers.keySet()) {
            String headerValue = headers.get(headerName);
            if (StringUtils.isNotBlank(headerValue)) {
                if (Objects.nonNull(httpPost)) {
                    httpPost.addHeader(headerName, headerValue);
                } else {
                    httpGet.addHeader(headerName, headerValue);
                }
            }
        }
    }
}
