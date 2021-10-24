package com.hk.container.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import java.util.HashMap;
import java.util.Map;

/**
 * @author : HK意境
 * @ClassName : JsonUtil
 * @date : 2021/9/20 14:15
 * @description :
 * @Todo :
 * @Bug :
 * @Modified :
 * @Version : 1.0
 */
public class JsonUtil {
    /**
     * Bean对象转JSON
     *
     * @param object
     * @param dataFormatString
     * @return
     */
    public static String beanToJson(Object object, String dataFormatString) {
        if (object != null) {
            if (dataFormatString.equals("") || dataFormatString == null || dataFormatString.isEmpty()) {
                return JSONObject.toJSONString(object);
            }
            return JSON.toJSONStringWithDateFormat(object, dataFormatString);
        } else {
            return null;
        }
    }
    /**
     * Bean对象转JSON
     *
     * @param object
     * @return
     */
    public static String beanToJson(Object object) {
        if (object != null) {
            return JSON.toJSONString(object);
        } else {
            return null;
        }
    }
    /**
     * String转JSON字符串
     *
     * @param key
     * @param value
     * @return
     */
    public static String stringToJsonByFastjson(String key, String value) {
        if (key.isEmpty() || value.isEmpty()) {
            return null;
        }
        Map<String, String> map = new HashMap<String, String>();
        map.put(key, value);
        return beanToJson(map, null);
    }
    /**
     * 将json字符串转换成对象
     *
     * @param json
     * @param clazz
     * @return
     */
    public static Object jsonToBean(String json, Object clazz) {
        if (json.isEmpty() || clazz == null) {
            return null;
        }
        return JSON.parseObject(json, clazz.getClass());
    }
    /**
     * json字符串转map
     *
     * @param json
     * @return
     */
    @SuppressWarnings("unchecked")
    public static Map<String, Object> jsonToMap(String json) {
        if (json.isEmpty()) {
            return null;
        }
        return JSON.parseObject(json, Map.class);
    }
}

