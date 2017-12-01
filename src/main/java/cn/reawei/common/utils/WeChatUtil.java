package cn.reawei.common.utils;

import com.alibaba.fastjson.JSONObject;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Formatter;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class WeChatUtil {
    private final static String APPID = "jsapi_ticket";
    private final static String SECRET = "jsapi_ticket";

    public static Map<String, String> getWeChatSign(String url) {
        String api_ticket = getJsapiTicket();
        Map<String, String> ret = sign(api_ticket, url);
        if (!ret.isEmpty()) {
            return ret;
        }
        return null;
    }

    private static String getJsapiTicket() {
        String requestUrl = "https://api.weixin.qq.com/cgi-bin/token";
//        String params = "grant_type=client_credential&appid=" + appid + "&secret=" + secret + "";
        Map<String, String> params = new HashMap<>();
        params.put("grant_type", "client_credential");
        params.put("appid", APPID);
        params.put("secret", SECRET);
        String result =HttpUtils.get(requestUrl, null, null, params, false);
//        HttpRequestUtils.httpGet(requestUrl + params);
        String access_token = JSONObject.parseObject(result).getString("access_token");
        requestUrl = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?";
        Map<String, String> params2 = new HashMap<>();
        params2.put("access_token", access_token);
        params2.put("type", "jsapi");
//        params = "access_token=" + access_token + "&type=jsapi";
//        result = HttpRequestUtils.httpGet(requestUrl + params);
        result =HttpUtils.get(requestUrl, null, null, params, false);
        String jsapi_ticket = JSONObject.parseObject(result).getString("ticket");
        int activeTime = Integer.parseInt(JSONObject.parseObject(result).getString("expires_in"));
        return jsapi_ticket;
    }

    private static Map<String, String> sign(String jsapi_ticket, String url) {
        Map<String, String> ret = new HashMap<>();
        String nonce_str = create_nonce_str();
        String timestamp = create_timestamp();
        String string1;
        String signature = "";

        //注意这里参数名必须全部小写，且必须有序
        string1 = "jsapi_ticket=" + jsapi_ticket +
                "&noncestr=" + nonce_str +
                "&timestamp=" + timestamp +
                "&url=" + url;
        try {
            MessageDigest crypt = MessageDigest.getInstance("SHA-1");
            crypt.reset();
            crypt.update(string1.getBytes("UTF-8"));
            signature = byteToHex(crypt.digest());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        ret.put("url", url);
        ret.put("jsapi_ticket", jsapi_ticket);
        ret.put("nonceStr", nonce_str);
        ret.put("timestamp", timestamp);
        ret.put("signature", signature);

        return ret;
    }

    private static String byteToHex(final byte[] hash) {
        Formatter formatter = new Formatter();
        for (byte b : hash) {
            formatter.format("%02x", b);
        }
        String result = formatter.toString();
        formatter.close();
        return result;
    }

    private static String create_nonce_str() {
        return UUID.randomUUID().toString();
    }

    private static String create_timestamp() {
        return Long.toString(System.currentTimeMillis() / 1000);
    }

    public static void main(String[] args) {
        String jsapi_ticket = "jsapi_ticket";

        // 注意 URL 一定要动态获取，不能 hardcode
        String url = "http://example.com";
//        Map<String, String> ret = sign(jsapi_ticket, url);
        Map<String, String> ret = getWeChatSign(url);
        for (Map.Entry entry : ret.entrySet()) {
            System.out.println(entry.getKey() + ", " + entry.getValue());
        }
    }

    ;
}
