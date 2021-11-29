package com.risk.riskmanage.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.HashSet;
import java.util.Set;

public class CustomValueUtils {

    public static final Set<String> getFieldEnSet(String custom) {
        Set<String> fieldEns = new HashSet<>();
        if (custom != null && !"".equals(custom)) {
            JSONObject jsonObject = JSON.parseObject(custom);
            Object farr = jsonObject.get("farr");
            if (farr != null) {
                JSONArray jsonArray = JSONArray.parseArray(JSON.toJSONString(farr));
                if (jsonArray != null && jsonArray.size() > 0) {
                    for (Object o : jsonArray) {
                        JSONObject field = JSON.parseObject(JSON.toJSONString(o));
                        Object fieldEn = field.get("fieldEn");
                        if (fieldEn != null && !"".equals(fieldEn)) {
                            fieldEns.add(fieldEn.toString());
                        }
                    }
                }
            }
        }
        return fieldEns;
    }
}
