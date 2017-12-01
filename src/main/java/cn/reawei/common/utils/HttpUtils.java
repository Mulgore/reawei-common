package cn.reawei.common.utils;

import org.apache.commons.lang.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.SocketTimeoutException;
import java.net.URLEncoder;
import java.util.*;

import org.apache.commons.httpclient.*;
import org.apache.commons.httpclient.cookie.CookiePolicy;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.params.HttpConnectionManagerParams;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.httpclient.util.IdleConnectionTimeoutThread;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.util.EntityUtils;

public class HttpUtils {

    private static String DEFAULT_CHARSET = "UTF-8";

    /**
     * 连接超时时间，由bean factory设置，缺省为20秒钟
     */
    private static int defaultConnectionTimeout = 20 * 000;

    /**
     * 回应超时时间, 由bean factory设置，缺省为40秒钟
     */
    private static int defaultSoTimeout = 40 * 1000;

    /**
     * 闲置连接超时时间, 由bean factory设置，缺省为60秒钟
     */
    private static int defaultIdleConnTimeout = 60 * 000;

    private static int defaultMaxConnPerHost = 30;

    private static int defaultMaxTotalConn = 80;

    /**
     * HTTP连接管理器，该连接管理器必须是线程安全的.
     **/
    private static final HttpConnectionManager connectionManager;

    /**
     * 默认等待HttpConnectionManager返回连接超时（只有在达到最大连接数时起作用）：1秒
     */
    private static final long defaultHttpConnectionManagerTimeout = 3 * 1000;

    private static boolean isHttpProxyForwardActive = false;

    private static final HttpClient client;

    static {
        HttpConnectionManagerParams params = loadHttpConfFromSeting();
        connectionManager = new MultiThreadedHttpConnectionManager();
        connectionManager.setParams(params);

        IdleConnectionTimeoutThread ict = new IdleConnectionTimeoutThread();
        ict.addConnectionManager(connectionManager);
        ict.setConnectionTimeout(defaultIdleConnTimeout);
        ict.start();

        client = new HttpClient(connectionManager);
        client.getParams().setConnectionManagerTimeout(defaultHttpConnectionManagerTimeout);
    }

    private static HttpConnectionManagerParams loadHttpConfFromSeting() {
        HttpConnectionManagerParams params = new HttpConnectionManagerParams();
        params.setConnectionTimeout(defaultConnectionTimeout);
        params.setSoTimeout(defaultSoTimeout);

        params.setDefaultMaxConnectionsPerHost(defaultMaxConnPerHost);
        params.setMaxTotalConnections(defaultMaxTotalConn);
        return params;
    }

    public static String post(String url, String charset, Map<String, String> params, Map<String, String> headers, boolean useProxy) {
        StringBuffer sb = new StringBuffer();
        charset = (charset == null || "".equals(charset)) ? DEFAULT_CHARSET : charset;

        PostMethod method = new PostMethod(url);

        try {
            if (headers != null && headers.size() > 0) {
                for (String headerName : headers.keySet()) {
                    String headerVaule = headers.get(headerName);
                    if (StringUtils.isNotBlank(headerVaule)) {
                        method.addRequestHeader(headerName, headerVaule);
                    }
                }
            }
            if (params != null && params.size() > 0) {
                for (String paramName : params.keySet()) {
                    //method.addParameter(paramName, URLEncoder.encode(params.get(paramName), charset));
                    String paramVaule = params.get(paramName);
                    if (StringUtils.isNotBlank(paramVaule)) {
                        method.addParameter(paramName, paramVaule);
                    }
                }
            }
            int statusCode = 0;
            if (useProxy && isHttpProxyForwardActive) {//是否使用代理
                client.getHostConfiguration().setProxy("", 1);//设置代理服务器的ip地址和端口
                client.getParams().setAuthenticationPreemptive(true);//使用抢先认证
                statusCode = client.executeMethod(method);
            } else {
                //TODO temp save
                HttpClient temp = new HttpClient();
                temp.getParams().setIntParameter(CoreConnectionPNames.SO_TIMEOUT, 3000);
                temp.getParams().setIntParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 3000);
                statusCode = temp.executeMethod(method);
            }
            if (statusCode != HttpStatus.SC_OK) {
                return null;
            }
            BufferedReader br = new BufferedReader(new InputStreamReader(method.getResponseBodyAsStream(), charset));
            String line = null;
            while ((line = br.readLine()) != null) {
                sb.append(line);
                line = null;
            }
        } catch (HttpException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            method.releaseConnection();
        }

        return sb.toString();
    }
    public static String postZMK(String url, String charset, Map<String, Object> params, Map<String, String> headers, boolean useProxy) {
        StringBuffer sb = new StringBuffer();
        charset = (charset == null || "".equals(charset)) ? DEFAULT_CHARSET : charset;

        PostMethod method = new PostMethod(url);

        try {
            if (headers != null && headers.size() > 0) {
                for (String headerName : headers.keySet()) {
                    String headerVaule = headers.get(headerName);
                    if (StringUtils.isNotBlank(headerVaule)) {
                        method.addRequestHeader(headerName, headerVaule);
                    }
                }
            }
            if (params != null && params.size() > 0) {
                for (String paramName : params.keySet()) {
                    //method.addParameter(paramName, URLEncoder.encode(params.get(paramName), charset));
                    String paramVaule = params.get(paramName).toString();
                    if (StringUtils.isNotBlank(paramVaule)) {
                        method.addParameter(paramName, paramVaule);
                    }
                }
            }
            int statusCode = 0;
            if (useProxy && isHttpProxyForwardActive) {//是否使用代理
                client.getHostConfiguration().setProxy("", 1);//设置代理服务器的ip地址和端口
                client.getParams().setAuthenticationPreemptive(true);//使用抢先认证
                statusCode = client.executeMethod(method);
            } else {
                //TODO temp save
                HttpClient temp = new HttpClient();
                temp.getParams().setIntParameter(CoreConnectionPNames.SO_TIMEOUT, 3000);
                temp.getParams().setIntParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 3000);
                statusCode = temp.executeMethod(method);
            }
            if (statusCode != HttpStatus.SC_OK) {
                return null;
            }
            BufferedReader br = new BufferedReader(new InputStreamReader(method.getResponseBodyAsStream(), charset));
            String line = null;
            while ((line = br.readLine()) != null) {
                sb.append(line);
                line = null;
            }
        } catch (HttpException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            method.releaseConnection();
        }

        return sb.toString();
    }
    public static String postSHB(String url, String charset, Map<String, String> params, Map<String, String> headers, boolean useProxy) {
        StringBuffer sb = new StringBuffer();
        charset = (charset == null || "".equals(charset)) ? DEFAULT_CHARSET : charset;

        PostMethod method = new PostMethod(url);
        method.setRequestHeader("Referer","http://www.51xiaoqu.com/");

        try {
            if (headers != null && headers.size() > 0) {
                for (String headerName : headers.keySet()) {
                    String headerVaule = headers.get(headerName);
                    if (StringUtils.isNotBlank(headerVaule)) {
                        method.addRequestHeader(headerName, headerVaule);
                    }
                }
            }
            if (params != null && params.size() > 0) {
                for (String paramName : params.keySet()) {
                    //method.addParameter(paramName, URLEncoder.encode(params.get(paramName), charset));
                    String paramVaule = params.get(paramName);
                    if (StringUtils.isNotBlank(paramVaule)) {
                        method.addParameter(paramName, paramVaule);
                    }
                }
            }
            int statusCode = 0;
            if (useProxy && isHttpProxyForwardActive) {//是否使用代理
                client.getHostConfiguration().setProxy("", 1);//设置代理服务器的ip地址和端口
                client.getParams().setAuthenticationPreemptive(true);//使用抢先认证
                statusCode = client.executeMethod(method);
            } else {
                //TODO temp save
                HttpClient temp = new HttpClient();
                temp.getParams().setIntParameter(CoreConnectionPNames.SO_TIMEOUT, 3000);
                temp.getParams().setIntParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 3000);
                statusCode = temp.executeMethod(method);
            }
            if (statusCode != HttpStatus.SC_OK) {
                return null;
            }
            BufferedReader br = new BufferedReader(new InputStreamReader(method.getResponseBodyAsStream(), charset));
            String line = null;
            while ((line = br.readLine()) != null) {
                sb.append(line);
                line = null;
            }
        } catch (HttpException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            method.releaseConnection();
        }

        return sb.toString();
    }
    public static String postMb(String url, String charset, Map<String, String> params, Map<String, String> headers, boolean useProxy) {
        StringBuffer sb = new StringBuffer();
        charset = (charset == null || "".equals(charset)) ? DEFAULT_CHARSET : charset;

        PostMethod method = new PostMethod(url);

        try {
            if (headers != null && headers.size() > 0) {
                for (String headerName : headers.keySet()) {
                    String headerVaule = headers.get(headerName);
                    if (StringUtils.isNotBlank(headerVaule)) {
                        method.addRequestHeader(headerName, headerVaule);
                    }
                }
            }
            if (params != null && params.size() > 0) {
                for (String paramName : params.keySet()) {
                    //method.addParameter(paramName, URLEncoder.encode(params.get(paramName), charset));
                    String paramVaule = params.get(paramName);
                    if (StringUtils.isNotBlank(paramVaule)) {
                        method.addParameter(paramName, paramVaule);
                    }
                }
            }
            int statusCode = 0;
            if (useProxy && isHttpProxyForwardActive) {//是否使用代理
                client.getHostConfiguration().setProxy("", 1);//设置代理服务器的ip地址和端口
                client.getParams().setAuthenticationPreemptive(true);//使用抢先认证
                statusCode = client.executeMethod(method);
            } else {
                //TODO temp save
                HttpClient temp = new HttpClient();
                temp.getParams().setIntParameter(CoreConnectionPNames.SO_TIMEOUT, 60000);
                temp.getParams().setIntParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 60000);
                statusCode = temp.executeMethod(method);
            }
            if (statusCode != HttpStatus.SC_OK) {
                return null;
            }
            BufferedReader br = new BufferedReader(new InputStreamReader(method.getResponseBodyAsStream(), charset));
            String line = null;
            while ((line = br.readLine()) != null) {
                sb.append(line);
                line = null;
            }
        } catch (HttpException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            method.releaseConnection();
        }

        return sb.toString();
    }
    public static String postRH(String url, String charset, Map<String, String> params, Map<String, String> headers, boolean useProxy) {
        StringBuffer sb = new StringBuffer();
        charset = (charset == null || "".equals(charset)) ? DEFAULT_CHARSET : charset;

        PostMethod method = new PostMethod(url);

        try {
            if (headers != null && headers.size() > 0) {
                for (String headerName : headers.keySet()) {
                    String headerVaule = headers.get(headerName);
                    if (StringUtils.isNotBlank(headerVaule)) {
                        method.addRequestHeader(headerName, headerVaule);
                    }
                }
            }
            if (params != null && params.size() > 0) {
                for (String paramName : params.keySet()) {
                    //method.addParameter(paramName, URLEncoder.encode(params.get(paramName), charset));
                    String paramVaule = params.get(paramName);
                    if (StringUtils.isNotBlank(paramVaule)) {
                        method.addParameter(paramName, paramVaule);
                    }
                }
            }
            int statusCode = 0;
            if (useProxy && isHttpProxyForwardActive) {//是否使用代理
                client.getHostConfiguration().setProxy("", 1);//设置代理服务器的ip地址和端口
                client.getParams().setAuthenticationPreemptive(true);//使用抢先认证
                statusCode = client.executeMethod(method);
            } else {
                //TODO temp save
                HttpClient temp = new HttpClient();
                temp.getParams().setIntParameter(CoreConnectionPNames.SO_TIMEOUT, 30000);
                temp.getParams().setIntParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 30000);
                statusCode = temp.executeMethod(method);
            }
            if (statusCode != HttpStatus.SC_OK) {
                return null;
            }
            BufferedReader br = new BufferedReader(new InputStreamReader(method.getResponseBodyAsStream(), charset));
            String line = null;
            while ((line = br.readLine()) != null) {
                sb.append(line);
                line = null;
            }
        } catch (HttpException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            method.releaseConnection();
        }

        return sb.toString();
    }

    public static String post(String url, String charset, Map<String, String> params) {
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("Content-Type", "application/x-www-form-urlencoded;charset=" + charset);

        return post(url, charset, params, headers, false);
    }
    public static String postZMK(String url, String charset, Map<String, Object> params) {
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("Content-Type", "application/x-www-form-urlencoded;charset=" + charset);

        return postZMK(url, charset, params, headers, false);
    }
    public static String postCC(String url, String charset, Map<String, String> params) {
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("Content-Type", "application/x-www-form-urlencoded;charset=" + charset);
        headers.put("Cookie",params.get("cookie").toString());
        return post(url, charset, params, headers, false);
    }
    public static String postSHB(String url, String charset, Map<String, String> params) {
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("Content-Type", "application/x-www-form-urlencoded;charset=" + charset);

        return postSHB(url, charset, params, headers, false);
    }
    public static String postMb(String url, String charset, Map<String, String> params) {
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("Content-Type", "application/x-www-form-urlencoded;charset=" + charset);

        return postMb(url, charset, params, headers, false);
    }
    public static String postRH(String url, String charset, Map<String, String> params) {
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("Content-Type", "application/x-www-form-urlencoded;charset=" + charset);
        return postRH(url, charset, params, headers, false);
    }
    public static String postZXNJ(String url, String charset, Map<String, String> params,String Source) {
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("Content-Type", "application/x-www-form-urlencoded;charset=" + charset);
        headers.put("Xposp-Authorization", "noneed");
        headers.put("Request-Source", Source);
        return postMb(url, charset, params, headers, false);
    }

    public static String post(String url, String charset, Map<String, String> params, String referer) {
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("Content-Type", "application/x-www-form-urlencoded;charset=" + charset);
        headers.put("Referer", referer);

        return post(url, charset, params, headers, false);
    }

    public static String post(String url, String charset, Map<String, String> params, boolean useProxy) {
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("Content-Type", "application/x-www-form-urlencoded;charset=" + charset);

        return post(url, charset, params, headers, useProxy);
    }

    public static String postJson(String url, String json, String charset) {
        String resText = null;
        CloseableHttpClient httpClient = HttpClients.createDefault();
        StringEntity requestEntity = new StringEntity(json, ContentType.APPLICATION_JSON);
        HttpPost postMethod = new HttpPost(url);

        postMethod.setEntity(requestEntity);
        try {
            HttpResponse response = httpClient.execute(postMethod);
            resText = EntityUtils.toString(response.getEntity(), charset);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return resText;
    }

    public static String post(String url, String xmlParam, String charset) {
        String resText = null;
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost post = new HttpPost(url);
        try {
            post.setEntity(new StringEntity(xmlParam, charset));
            CloseableHttpResponse response = httpClient.execute(post);
            resText = EntityUtils.toString(response.getEntity(), charset);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return resText;
    }

    public static String post2(String url, String xmlParam, String charset) {
        String resText = null;
        CloseableHttpClient httpClient = HttpClients.createDefault();
        httpClient.getParams().setIntParameter(CoreConnectionPNames.SO_TIMEOUT, 3000);
        httpClient.getParams().setIntParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 3000);
        HttpPost post = new HttpPost(url);
        try {
            post.setEntity(new StringEntity(xmlParam, charset));
            CloseableHttpResponse response = httpClient.execute(post);
            resText = EntityUtils.toString(response.getEntity(), charset);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return resText;
    }

    public static byte[] post(String url, Map<String, String> headers, RequestEntity requestEntity) throws Exception {
        return post(url, headers, requestEntity, false);
    }

    public static byte[] post(String url, Map<String, String> headers, RequestEntity requestEntity, boolean useProxy) throws Exception {
        byte[] respData = null;

        PostMethod method = new PostMethod(url);
        if (headers != null && headers.size() > 0) {
            for (String headerName : headers.keySet()) {
                method.addRequestHeader(headerName, headers.get(headerName));
            }
        }
        method.getParams().setCookiePolicy(CookiePolicy.IGNORE_COOKIES);
        method.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler(0, false));
        method.setRequestEntity(requestEntity);

        try {
            int statusCode = 0;
            if (useProxy && isHttpProxyForwardActive) {//是否使用代理
                client.getHostConfiguration().setProxy("", 1);//设置代理服务器的ip地址和端口
                client.getParams().setAuthenticationPreemptive(true);//使用抢先认证
                statusCode = client.executeMethod(method);
            } else {
                //TODO temp save
                HttpClient temp = new HttpClient();
                statusCode = temp.executeMethod(method);
            }
            if (statusCode != HttpStatus.SC_OK) {
                throw new RuntimeException("UPMP network connect failed for url[" + url + "]");
            }
            respData = method.getResponseBody();
        } catch (SocketTimeoutException e) {
            return null;
        } catch (Exception e) {
            return null;
        } finally {
            method.releaseConnection();
            ((SimpleHttpConnectionManager) client.getHttpConnectionManager()).shutdown();
        }
        return respData;
    }

    public static byte[] post2(String url, Map<String, String> headers, RequestEntity requestEntity, boolean useProxy) throws Exception {
        byte[] respData = null;

        PostMethod method = new PostMethod(url);
        if (headers != null && headers.size() > 0) {
            for (String headerName : headers.keySet()) {
                method.addRequestHeader(headerName, headers.get(headerName));
            }
        }
        method.getParams().setCookiePolicy(CookiePolicy.IGNORE_COOKIES);
        method.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler(0, false));
        method.setRequestEntity(requestEntity);

        try {
            int statusCode = 0;
            if (useProxy && isHttpProxyForwardActive) {//是否使用代理
                client.getHostConfiguration().setProxy("", 1);//设置代理服务器的ip地址和端口
                client.getParams().setAuthenticationPreemptive(true);//使用抢先认证

                statusCode = client.executeMethod(method);
            } else {
                //TODO temp save
                HttpClient temp = new HttpClient();
                temp.getParams().setIntParameter(CoreConnectionPNames.SO_TIMEOUT, 3000);
                temp.getParams().setIntParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 3000);
                statusCode = temp.executeMethod(method);
            }
            if (statusCode != HttpStatus.SC_OK) {
                throw new RuntimeException("UPMP network connect failed for url[" + url + "]");
            }
            respData = method.getResponseBody();
        } catch (SocketTimeoutException e) {
            return null;
        } catch (Exception e) {
            return null;
        } finally {
            method.releaseConnection();
            ((SimpleHttpConnectionManager) client.getHttpConnectionManager()).shutdown();
        }
        return respData;
    }


    public static String get(String url, String charset,  Map<String, String> params) {
        HashMap headers = new HashMap();
        headers.put("Content-Type", "application/x-www-form-urlencoded;charset=" + charset);
        return get(url, charset, headers, params, false);
    }

    public static String get(String url, String charset, Map<String, String> headers, Map<String, String> params, boolean useProxy) {
        StringBuffer sb = new StringBuffer();
        charset = (charset == null || "".equals(charset)) ? DEFAULT_CHARSET : charset;

        GetMethod method = new GetMethod(url + "?" + mapToQueryString(params, charset));

        try {
            method.getParams().setCredentialCharset(charset);
            if (headers != null && headers.size() > 0) {
                for (String headerName : headers.keySet()) {
                    String headerVaule = headers.get(headerName);
                    if (StringUtils.isNotBlank(headerVaule)) {
                        method.addRequestHeader(headerName, headerVaule);
                    }
                }
            }
            int statusCode = 0;
            if (useProxy && isHttpProxyForwardActive) {//是否使用代理
                client.getHostConfiguration().setProxy("", 1);//设置代理服务器的ip地址和端口
                client.getParams().setAuthenticationPreemptive(true);//使用抢先认证
                statusCode = client.executeMethod(method);
            } else {
                //TODO temp save
                HttpClient temp = new HttpClient();
                statusCode = temp.executeMethod(method);
            }
            if (statusCode != HttpStatus.SC_OK) {
                return null;
            }
            BufferedReader br = new BufferedReader(new InputStreamReader(method.getResponseBodyAsStream(), charset));
            String line = null;
            while ((line = br.readLine()) != null) {
                sb.append(line);
                line = null;
            }
        } catch (HttpException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return sb.toString();
    }

    private static String mapToQueryString(Map<String, String> params, String charset) {
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

    /**
     * 把请求要素按照“参数=参数值”的模式用“&”字符拼接成字符串
     *
     * @param params  请求要素
     * @param charset URL编码
     * @return 拼接成的字符串
     */
    public static String createLinkString(Map<String, String> params, String charset, boolean isNeedSort) {
        StringBuilder sb = new StringBuilder();
        List<String> keys = new ArrayList<String>(params.keySet());
        if (isNeedSort) {//字段是否需要排序
            Collections.sort(keys);
        }
        for (int i = 0; i < keys.size(); i++) {
            String key = keys.get(i);
            String value = params.get(key);
            if (StringUtils.isBlank(value)) {
                continue;
            }
            if (StringUtils.isNotBlank(charset)) {
                try {
                    value = URLEncoder.encode(value, charset);
                } catch (UnsupportedEncodingException e) {

                }
            }
            if (i == keys.size() - 1) {//拼接时，不包括最后一个&字符
                sb.append(key).append("=").append(value);
            } else {
                sb.append(key).append("=").append(value).append("&");
            }
        }
        return sb.toString();
    }
}

