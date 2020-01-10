package com.home.examination.common.config;

import java.util.HashMap;
import java.util.Map;

public class DataDictionaryHandler {
    // 志愿-是否发布
    private static final Map<String, String> volunteerStatusMap = new HashMap<>(2);
   static {
       // 志愿-是否发布
       volunteerStatusMap.put("0", "草稿");
       volunteerStatusMap.put("1", "发布");

    }

    /**
     * 根据类型，code获取文本
     * @param type
     * @param code
     * @return
     */
    public static String getContentByType(String type, String code) {
       String result = "";
       if(type.equals("volunteer_status")) result = volunteerStatusMap.get(code);
        return result;
    }

}