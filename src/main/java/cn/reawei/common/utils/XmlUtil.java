package cn.reawei.common.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.xml.sax.InputSource;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.io.StringReader;
import java.util.*;

public class XmlUtil {

    /**
     * xml转json字符串 注意:路径和字符串二传一另外一个传null<br>
     * 方 法 名：xmlToJson <br>
     * 创 建 人：xingwu<br>
     * 创建时间：2017年12月15日 上午10:48:26 <br>
     * 修 改 人： <br>
     * 修改日期： <br>
     *
     * @param xmlPath xml路径(和字符串二传一,两样都传优先使用路径)
     * @param xmlStr  xml字符串(和路径二传一,两样都传优先使用路径)
     * @return String
     */
    public static String xmlToJson(String xmlPath, String xmlStr) {
        SAXBuilder sbder = new SAXBuilder();
        Map<String, Object> map = new HashMap<>();
        Document document;
        try {
            if (xmlPath != null) {
                //路径
                document = sbder.build(new File(xmlPath));
            } else if (xmlStr != null) {
                //xml字符
                StringReader reader = new StringReader(xmlStr);
                InputSource ins = new InputSource(reader);
                document = sbder.build(ins);
            } else {
                return "{}";
            }
            //获取根节点
            Element el = document.getRootElement();
            List<Element> eList = el.getChildren();
            Map<String, Object> rootMap = new HashMap<>();
            //得到递归组装的map
            rootMap = xmlToMap(eList, rootMap);
            map.put(el.getName(), rootMap);
            //将map转换为json 返回
            return JSON.toJSONString(map);
        } catch (Exception e) {
            return "{}";
        }
    }

    /**
     * json转xml<br>
     * 方 法 名：jsonToXml <br>
     * 创 建 人：xingwu<br>
     * 创建时间：2017年12月15日 上午10:48:26 <br>
     * 修 改 人： <br>
     * 修改日期： <br>
     *
     * @param json
     * @return String
     */
    public static String jsonToXml(String json) {
        try {
            StringBuffer buffer = new StringBuffer();
            buffer.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
            JSONObject jObj = JSONObject.parseObject(json);
            jsonToXmlstr(jObj, buffer);
            return buffer.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * json转str<br>
     * 方 法 名：jsonToXmlstr <br>
     * 创 建 人：xingwu<br>
     * 创建时间：2017年12月15日 上午10:48:26 <br>
     * 修 改 人： <br>
     * 修改日期： <br>
     *
     * @param jObj
     * @param buffer
     * @return String
     */
    public static String jsonToXmlstr(JSONObject jObj, StringBuffer buffer) {
        Set<Map.Entry<String, Object>> se = jObj.entrySet();
        for (Iterator<Map.Entry<String, Object>> it = se.iterator(); it.hasNext(); ) {
            Map.Entry<String, Object> en = it.next();
            if (en.getValue().getClass().getName().equals("com.alibaba.fastjson.JSONObject")) {
                buffer.append("<" + en.getKey() + ">");
                JSONObject jo = jObj.getJSONObject(en.getKey());
                jsonToXmlstr(jo, buffer);
                buffer.append("</" + en.getKey() + ">");
            } else if (en.getValue().getClass().getName().equals("com.alibaba.fastjson.JSONArray")) {
                JSONArray jarray = jObj.getJSONArray(en.getKey());
                for (int i = 0; i < jarray.size(); i++) {
                    buffer.append("<" + en.getKey() + ">");
                    JSONObject jsonobject = jarray.getJSONObject(i);
                    jsonToXmlstr(jsonobject, buffer);
                    buffer.append("</" + en.getKey() + ">");
                }
            } else if (en.getValue().getClass().getName().equals("java.lang.String")) {
                buffer.append("<" + en.getKey() + ">" + en.getValue());
                buffer.append("</" + en.getKey() + ">");
            }

        }
        return buffer.toString();
    }

    public static String xmlToJSON(String xml) {
        try {
            InputStream is = new ByteArrayInputStream(xml.getBytes("utf-8"));
            SAXBuilder sb = new SAXBuilder();
            Document doc = sb.build(is);
            Element root = doc.getRootElement();
            Map<String, Object> map = new HashMap<>();
            List<Element> eList = root.getChildren();
            xmlToMap(eList, map);
            return JSONObject.toJSONString(map);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    public static Map<String, Object> xmlToMap(List<Element> eList, Map<String, Object> map) {
        for (Element e : eList) {
            Map<String, Object> eMap = new HashMap<>();
            List<Element> elementList = e.getChildren();
            if (elementList != null && elementList.size() > 0) {
                eMap = xmlToMap(elementList, eMap);
                Object obj = map.get(e.getName());
                if (obj != null) {
                    List<Object> olist = new ArrayList<>();
                    if (obj.getClass().getName().equals("java.util.HashMap")) {
                        olist.add(obj);
                        olist.add(eMap);

                    } else if (obj.getClass().getName().equals("java.util.ArrayList")) {
                        olist = (List<Object>) obj;
                        olist.add(eMap);
                    }
                    map.put(e.getName(), olist);
                } else {
                    map.put(e.getName(), eMap);
                }
            } else {
                map.put(e.getName(), e.getValue());
            }
        }
        return map;
    }
}
