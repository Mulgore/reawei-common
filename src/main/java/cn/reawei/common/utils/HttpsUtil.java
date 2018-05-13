package cn.reawei.common.utils;

import org.apache.http.*;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Created in 2018/5/13 11:11
 *
 * @author qigong
 */
public class HttpsUtil {

    private static final String HTTPS = "https";
    private static final String CONTENT_TYPE = "Content-Type";
    private static SSLConnectionSocketFactory sslSf = null;
    private static PoolingHttpClientConnectionManager cm = null;

    static {
        try {
            SSLContextBuilder builder = new SSLContextBuilder();
            // 全部信任 不做身份鉴定
            builder.loadTrustMaterial(null, (TrustStrategy) (x509Certificates, s) -> true);
            sslSf = new SSLConnectionSocketFactory(builder.build(), new String[]{"SSLv2Hello", "SSLv3", "TLSv1", "TLSv1.2"}, null, NoopHostnameVerifier.INSTANCE);
            Registry<ConnectionSocketFactory> registry = RegistryBuilder.<ConnectionSocketFactory>create()
                    .register(HTTPS, sslSf)
                    .build();
            cm = new PoolingHttpClientConnectionManager(registry);
            //max connection
            cm.setMaxTotal(200);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String doGet(String url) {
        Map<String, String> headers = new HashMap<>(16);
        headers.put("Content-Type", "application/json; charset=utf-8");
        headers.put("Accept", "text/plain;charset=utf-8");
        return httpsGet(url, headers, null, "utf-8");
    }

    public static String doGet(String url, Map<String, Object> params) {
        Map<String, String> headers = new HashMap<>(16);
        headers.put("Content-Type", "application/json; charset=utf-8");
        headers.put("Accept", "text/plain;charset=utf-8");
        return httpsGet(url, headers, params, "utf-8");
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
        return httpsGet(url, headers, params, defaultCharset);
    }

    public static String doPost(String url) {
        Map<String, String> headers = new HashMap<>(16);
        headers.put("Content-Type", "application/x-www-form-urlencoded");
        return httpsPost(url, headers, null, "utf-8");
    }

    public static String doPost(String url, Map<String, Object> params) {
        Map<String, String> headers = new HashMap<>(16);
        headers.put("Content-Type", "application/x-www-form-urlencoded");
        return httpsPost(url, headers, params, "utf-8");
    }

    public static String doPost(String url, Map<String, Object> params, Map<String, String> headers, String charset) {
        String defaultCharset = "utf-8";
        if (Objects.nonNull(charset) && charset.length() > 0) {
            defaultCharset = charset;
        }
        if (StringUtil.isBlank(headers.get(CONTENT_TYPE))) {
            headers.put("Content-Type", "application/x-www-form-urlencoded");
        }
        return httpsPost(url, headers, params, defaultCharset);
    }

    /**
     * httpClient post请求
     *
     * @param url    请求url
     * @param header 头部信息
     * @param params 请求参数 form提交适用
     * @return 可能为空 需要处理
     */
    private static String httpsPost(String url, Map<String, String> header, Map<String, Object> params, String charset) {
        String result;
        CloseableHttpClient httpClient = null;
        try {
            httpClient = getHttpClient();
            HttpPost httpPost = new HttpPost(url);
            // 设置头信息
            HttpUtil.setHeaders(header, httpPost, null);
            // 设置请求参数
            HttpUtil.mapToPostParams(params, charset, httpPost);
            HttpResponse httpResponse = httpClient.execute(httpPost);
            HttpEntity resEntity = httpResponse.getEntity();
            return EntityUtils.toString(resEntity);
        } catch (Exception e) {
            result = "接口请求异常";
        } finally {
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
     * httpClient get请求
     *
     * @param url    请求url
     * @param header 头部信息
     * @param params 请求参数 form提交适用
     * @return 可能为空 需要处理
     */
    private static String httpsGet(String url, Map<String, String> header, Map<String, Object> params, String charset) {
        String result;
        CloseableHttpClient httpClient = null;
        try {
            httpClient = getHttpClient();
            HttpGet httpGet = new HttpGet(url + HttpUtil.mapToQueryString(params, charset));
            // 设置头信息
            HttpUtil.setHeaders(header, null, httpGet);
            // 设置请求参数
            HttpResponse httpResponse = httpClient.execute(httpGet);
            HttpEntity resEntity = httpResponse.getEntity();
            return EntityUtils.toString(resEntity);
        } catch (Exception e) {
            result = "接口请求异常";
        } finally {
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

    private static CloseableHttpClient getHttpClient() {
        return HttpClients.custom().setSSLSocketFactory(sslSf).setConnectionManager(cm).setConnectionManagerShared(true).build();
    }
}
