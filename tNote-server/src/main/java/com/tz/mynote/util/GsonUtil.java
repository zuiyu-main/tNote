package com.tz.mynote.util;

import com.google.gson.Gson;

import java.util.Map;

/**
 * @author tz
 * @Classname GsonUtil
 * @Description
 * @Date 2019-09-06 09:34
 */
public class GsonUtil {
    /**
     * 对象转json
     * @param obj
     * @return
     */
    public static String toJson(Object obj){
        Gson gson = new Gson();
        return gson.toJson(obj);
    }

    /**
     * josn 转map
     * @param json
     * @return
     */
    public static Object fromJsonToMap(String json){
        Gson gson = new Gson();
        return gson.fromJson(json,Map.class);
    }
}
