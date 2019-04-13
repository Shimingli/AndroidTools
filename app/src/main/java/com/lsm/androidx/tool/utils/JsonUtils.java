package com.lsm.androidx.tool.utils;

import android.text.TextUtils;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONObject;

public class JsonUtils {
    public static String removeBOM(String data) {
        if (!TextUtils.isEmpty(data) && data.startsWith("﻿")) {
            return data.substring(1);
        }
        return data;
    }

    public static Map<String, Object> getDataMap(String data) {
        if (data != null) {
            try {
                Map<String, Object> map = jsonToMap(new JSONObject(data));
                if (map != null) {
                    return map;
                }
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
        return null;
    }

    public static Map<String, Object> jsonToMap(JSONObject json) throws Exception {
        Map<String, Object> ret = new HashMap();
        Iterator<?> keys = json.keys();
        while (keys.hasNext()) {
            String key = (String) keys.next();
            Object value = json.get(key);
            if (value instanceof JSONObject) {
                ret.put(key, jsonToMap((JSONObject) value));
            } else if (value instanceof JSONArray) {
                ret.put(key, jsonToArray((JSONArray) value));
            } else if (value instanceof String) {
                ret.put(key, value);
            } else if (value instanceof Number) {
                ret.put(key, value);
            } else if (value == JSONObject.NULL) {
                ret.put(key, null);
            }
        }
        return ret;
    }

    public static List<Object> jsonToArray(JSONArray arr) throws Exception {
        List<Object> ret = new ArrayList(arr.length());
        for (int i = 0; i < arr.length(); i++) {
            Object elem = arr.get(i);
            if (elem instanceof JSONObject) {
                ret.add(jsonToMap((JSONObject) elem));
            } else if (elem instanceof JSONArray) {
                ret.add(jsonToArray((JSONArray) elem));
            } else if (elem instanceof String) {
                ret.add(elem);
            } else if (elem instanceof Number) {
                ret.add(elem);
            }
        }
        return ret;
    }
}
