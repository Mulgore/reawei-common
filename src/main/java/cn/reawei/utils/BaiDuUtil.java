package cn.reawei.utils;

import com.alibaba.fastjson.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

public class BaiDuUtil {

    /**
     * 根据经纬度信息获取结构化地址
     *
     * @param ak
     * @param address
     * @return
     */
    public static Map<String, String> getLngLatByAddress(String ak, String address) {
        Map<String, String> lngLatMap = new HashMap<>();
        String url;
        try {
            url = "http://api.map.baidu.com/geocoder/v2/?ak=" + ak + "&address=" + URLEncoder.encode(address, "UTF-8") + "&output=json";
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return lngLatMap;
        }
        String responseMsg = HttpUtils.get(url, "UTF-8", null, null, true);
        JSONObject responseJson = JSONObject.parseObject(responseMsg);
        int status = responseJson.getIntValue("status");
        int RESPONSE_OK = 0;
        if (RESPONSE_OK == status) {
            JSONObject location = responseJson.getJSONObject("result").getJSONObject("location");
            lngLatMap.put("lng", String.valueOf(location.getFloat("lng")));
            lngLatMap.put("lat", String.valueOf(location.getFloat("lat")));
        }
        return lngLatMap;
    }

    /**
     * 根据经纬度信息获取结构化地址
     *
     * @param ak
     * @param Latitude
     * @param Longtitude
     * @return
     */
    public static Map<String, String> getStructAddressByGeocoding(String ak, String Latitude, String Longtitude) {
        Map<String, String> addressMap = new HashMap<>();
        String url = "http://api.map.baidu.com/geocoder/v2/?ak=" + ak + "&location=" + Latitude + "," + Longtitude + "&output=json&pois=1";
        String responseMsg = HttpUtils.get(url, "utf-8", null, null, true);
        JSONObject responseJson = JSONObject.parseObject(responseMsg);
        int status = responseJson.getIntValue("status");
        int RESPONSE_OK = 0;
        if (RESPONSE_OK == status) {
            JSONObject resultObj = JSONObject.parseObject(responseJson.getString("result"));
            JSONObject addressComponentObj = JSONObject.parseObject(resultObj.getString("addressComponent"));
            addressMap.put("province", addressComponentObj.getString("province"));
            addressMap.put("city", addressComponentObj.getString("city"));
            addressMap.put("district", addressComponentObj.getString("district"));
            addressMap.put("street", addressComponentObj.getString("street"));
            addressMap.put("street_number", addressComponentObj.getString("street_number"));
        }
        return addressMap;
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        String ak = "w1yteHTTXUzLPfCVnm5Y9sYo";
        String Longtitude = "118.78063";
        String Latitude = "32.02117";
        Map<String, String> result = getStructAddressByGeocoding(ak, Latitude, Longtitude);
        System.out.println("province :" + result.get("province"));
        System.out.println("city :" + result.get("city"));
        System.out.println("district :" + result.get("district"));
        System.out.println("street :" + result.get("street"));
        System.out.println("street_number :" + result.get("street_number"));
    }

}

