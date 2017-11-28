package cn.reawei.utils;

import cn.reawei.enums.BaiDu;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.*;

public class BaiDuUtil {

    /**
     * 根据经纬度信息获取结构化地址
     *
     * @param ak      ak
     * @param address 地址
     */
    public static Map<String, Object> getLngLatByAddress(String ak, String address) {
        Map<String, Object> lngLatMap = new HashMap<>();
        String url;
        try {
            url = "http://api.map.baidu.com/geocoder/v2/?address=" + URLEncoder.encode(address, "UTF-8") + "&ak=" + ak + "&output=json";
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return lngLatMap;
        }
        String responseMsg = HttpUtils.get(url, "UTF-8", null, null, true);
        String jsonObject = XmlUtil.xmlToJson(null, responseMsg);
        JSONObject responseJson = JSONObject.parseObject(jsonObject);
        int status = responseJson.getIntValue("status");
        int RESPONSE_OK = 0;
        if (RESPONSE_OK == status) {
            JSONObject locations = responseJson.getJSONObject("GeocoderSearchResponse").getJSONObject("result").getJSONObject("location");
            if (Objects.nonNull(locations)) {
                lngLatMap.put("longitude", String.valueOf(locations.get("lng")));
                lngLatMap.put("latitude", String.valueOf(locations.get("lat")));
            }
        }
        return lngLatMap;
    }

    /**
     * 根据经纬度信息获取结构化地址
     *
     * @param ak        ak
     * @param latitude  纬度
     * @param longitude 经度
     */
    public static Map<String, String> getStructAddressByGeocoding(String ak, String latitude, String longitude) {
        Map<String, String> addressMap = new HashMap<>();
        String url = "http://api.map.baidu.com/geocoder/v2/?ak=" + ak + "&location=" + latitude + "," + longitude + "&output=json&pois=1";
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
     * IP 地址获取地址详细信息
     *
     * @param ak ak
     * @param ip Ip
     */
    public static Map<String, Object> getStructAddressByIP(String ak, String ip) {
        Map<String, Object> addressMap = new HashMap<>();
        String url = "http://api.map.baidu.com/location/ip?ip=" + ip + "&ak=" + ak + "&coor=bd09ll";
        String responseMsg = HttpUtils.get(url, "utf-8", null, null, true);
        JSONObject responseJson = JSONObject.parseObject(responseMsg);
        int status = responseJson.getIntValue("status");
        int RESPONSE_OK = 0;
        if (Objects.equals(RESPONSE_OK, status)) {
            JSONObject resultObj = JSONObject.parseObject(responseJson.getString("content"));
            JSONObject addressComponentObj = JSONObject.parseObject(resultObj.getString("address_detail"));
            addressMap.put("province", addressComponentObj.getString("province"));
            addressMap.put("city", addressComponentObj.getString("city"));
            addressMap.put("district", addressComponentObj.getString("district"));
            addressMap.put("street", addressComponentObj.getString("street"));
            addressMap.put("street_number", addressComponentObj.getString("street_number"));
            JSONObject latLon = JSONObject.parseObject(resultObj.getString("point"));
            addressMap.put("longitude", latLon.getString("x"));
            addressMap.put("latitude", latLon.getString("y"));
        }
        return addressMap;
    }

    /**
     * 获取公交
     *
     * @param ak          ak
     * @param origin      起点经纬度
     * @param destination 终点经纬度
     */
    public static Map<String, Object> getTransit(String ak, String origin, String destination) {
        Map<String, Object> result1 = getLngLatByAddress(ak, origin);
        Map<String, Object> result2 = getLngLatByAddress(ak, destination);
        origin = result1.get("latitude") + "," + result1.get("longitude");
        destination = result2.get("latitude") + "," + result2.get("longitude");
        Map<String, Object> addressMap = new HashMap<>();
        String url = "http://api.map.baidu.com/direction/v2/transit";
        Map<String, String> params = new HashMap<>();
        params.put("origin", origin);
        params.put("destination", destination);
        params.put("ak", ak);
        params.put("output", "json");
        String responseMsg = HttpUtils.get(url, "utf-8", null, params, true);
        JSONObject responseJson = JSONObject.parseObject(responseMsg);
        int status = responseJson.getIntValue("status");
        int RESPONSE_OK = 0;
        if (Objects.equals(RESPONSE_OK, status)) {
            JSONObject result = JSONObject.parseObject(responseJson.getString("result"));
            addressMap.put("total", result.getString("total"));
            JSONArray resultObj = result.getJSONArray("routes");
            List<Map<String, Object>> routes = new ArrayList<>();
            for (Object item : resultObj) {
                JSONObject obj = (JSONObject) item;
                Map<String, Object> data = new HashMap<>();
                data.put("duration", obj.getString("duration"));
                data.put("price", obj.getString("price"));
                data.put("distance", obj.getString("distance"));
                data.put("arrive_time", obj.getString("arrive_time"));
                JSONArray steps = obj.getJSONArray("steps");
                List<String> resultStep = new ArrayList<>();
                for (Object step : steps) {
                    List stepss = (List) step;
                    JSONObject stepObj = (JSONObject) stepss.get(0);
                    resultStep.add(stepObj.getString("instructions"));
                }
                data.put("steps", JSONObject.toJSONString(resultStep));
                routes.add(data);
            }
            addressMap.put("steps", routes);
        }
        return addressMap;
    }

    /**
     * 获取上车地点
     */
    public static Map<String, Object> getParkingByAddressOrIp(String ak, String address, String ip) {
        Map<String, Object> addressMap = new HashMap<>();
        Map<String, Object> result1;
        if (Objects.nonNull(address)) {
            result1 = getLngLatByAddress(ak, address);
        } else if (Objects.nonNull(ip)) {
            result1 = getStructAddressByIP(ak, ip);
        } else {
            return addressMap;
        }
        String location = result1.get("longitude") + "," + result1.get("latitude");
        String url = "http://api.map.baidu.com/parking/search";
        Map<String, String> params = new HashMap<>();
        params.put("location", location);
        params.put("ak", ak);
        params.put("coordtype", "bd09ll");
        String responseMsg = HttpUtils.get(url, "utf-8", null, params, true);
        JSONObject responseJson = JSONObject.parseObject(responseMsg);
        int status = responseJson.getIntValue("status");
        int RESPONSE_OK = 0;
        if (Objects.equals(RESPONSE_OK, status)) {
            JSONArray result = responseJson.getJSONArray("recommendStops");
            List<String> names = new ArrayList<>();
            if (Objects.nonNull(result)) {
                for (Object obj : result) {
                    JSONObject item = (JSONObject) obj;
                    names.add(item.getString("name"));
                }
            }
            addressMap.put("name", names);
        }
        return addressMap;
    }

    public static void main(String[] args) {
        String ak = "R62KkaKOTkOHC93qDWipbg0sON1Pwvc6";
        String ip = "183.129.21.159";
        String address = "浙江省杭州市火车东站";
//        Map<String, String> result = getStructAddressByIP(ak, ip);
//        System.out.println("province :" + result.get("province"));
//        System.out.println("city :" + result.get("city"));
//        System.out.println("district :" + result.get("district"));
//        System.out.println("street :" + result.get("street"));
//        System.out.println("street_number :" + result.get("street_number"));
//        System.out.println("lat :" + result.get("latitude"));
//        System.out.println("lon :" + result.get("longitude"));

        Map<String, Object> data = getParkingByAddressOrIp(BaiDu.BAI_DU_AK2.getAk(), null, ip);
        System.out.println(JSONObject.toJSONString(data));
    }


}

