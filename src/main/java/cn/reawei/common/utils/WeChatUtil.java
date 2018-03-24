package cn.reawei.common.utils;

import com.alibaba.fastjson.JSONObject;
import sun.misc.BASE64Decoder;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.MessageDigest;
import java.util.*;

/**
 * 微信开发工具类
 */
public class WeChatUtil {
    private final static String APP_ID = "jsapi_ticket";
    private final static String SECRET = "jsapi_ticket";

    public static Map<String, String> getWeChatSign(String url) {
        String api_ticket = getApiTicket();
        Map<String, String> ret = sign(api_ticket, url);
        if (!ret.isEmpty()) {
            return ret;
        }
        return null;
    }

    /**
     * @return 获取微信签名
     */
    private static String getApiTicket() {
        String requestUrl = "https://api.weixin.qq.com/cgi-bin/token";
        Map<String, String> params = new HashMap<>();
        params.put("grant_type", "client_credential");
        params.put("appid", APP_ID);
        params.put("secret", SECRET);
        String result = HttpUtils.get(requestUrl, "utf-8", null, params, true);
        String access_token = JSONObject.parseObject(result).getString("access_token");
        requestUrl = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?";
        Map<String, String> params2 = new HashMap<>();
        params2.put("access_token", access_token);
        params2.put("type", "jsapi");
        result = HttpUtils.get(requestUrl, "utf-8", null, params2, true);
//        int activeTime = Integer.parseInt(JSONObject.parseObject(result).getString("expires_in"));
        return JSONObject.parseObject(result).getString("ticket");
    }

    /**
     * 解析微信签名
     *
     * @param apiTicket 微信签名
     * @param url       请求地址
     */
    private static Map<String, String> sign(String apiTicket, String url) {
        Map<String, String> ret = new HashMap<>();
        String nonce_str = create_nonce_str();
        String timestamp = create_timestamp();
        String string1;
        String signature = "";

        //注意这里参数名必须全部小写，且必须有序
        string1 = "jsapi_ticket=" + apiTicket +
                "&noncestr=" + nonce_str +
                "&timestamp=" + timestamp +
                "&url=" + url;
        try {
            MessageDigest crypt = MessageDigest.getInstance("SHA-1");
            crypt.reset();
            crypt.update(string1.getBytes("UTF-8"));
            signature = byteToHex(crypt.digest());
        } catch (Exception e) {
            e.printStackTrace();
        }

        ret.put("url", url);
        ret.put("jsapi_ticket", apiTicket);
        ret.put("nonceStr", nonce_str);
        ret.put("timestamp", timestamp);
        ret.put("signature", signature);

        return ret;
    }

    /**
     * 加密
     *
     * @param hash 字节
     */
    private static String byteToHex(final byte[] hash) {
        Formatter formatter = new Formatter();
        for (byte b : hash) {
            formatter.format("%02x", b);
        }
        String result = formatter.toString();
        formatter.close();
        return result;
    }

    /**
     * 获取时间戳
     *
     * @return 时间戳
     */
    private static String create_nonce_str() {
        return UUID.randomUUID().toString();
    }

    /**
     * 获取时间戳
     *
     * @return 时间戳
     */
    private static String create_timestamp() {
        return Long.toString(System.currentTimeMillis() / 1000);
    }
//
//    private static String decrypt(String text, String aesKey, String ivKey) throws Exception {
//        byte[] original;
//        try {
//            // 设置解密模式为AES的CBC模式
//            Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
//            SecretKeySpec key_spec = new SecretKeySpec(aesKey.getBytes(), "AES");
//            IvParameterSpec iv = new IvParameterSpec(ivKey.getBytes(), 0, 24);
//            cipher.init(Cipher.DECRYPT_MODE, key_spec, iv);
//
//            // 使用BASE64对密文进行解码
//            byte[] encrypted = new BASE64Decoder().decodeBuffer(text);
//            // 解密
//            original = cipher.doFinal(encrypted);
//            System.out.println(original);
//            return new String(original, "UTF-8");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return null;
//    }

    
}
