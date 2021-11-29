package com.risk.riskmanage.util;

import com.github.pagehelper.PageInfo;

import java.util.HashMap;
import java.util.Map;

public class ResponseUtil {
    public static Map<String,Object> getResponseMap(PageInfo pageInfo){
        HashMap<String, Object> responseMap = new HashMap<>();
        responseMap.put("pageInfo",pageInfo);
        responseMap.put("klist",pageInfo.getList());
        pageInfo.setList(null);
        return responseMap;
    }
}
