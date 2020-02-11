package com.home.examination.entity.vo;

import lombok.Data;

import java.util.Map;

@Data
public class SchoolFeaturesVO {

    /**
     * 院校类别
     */
    private Map<String, String> schoolTypeMap;

    /**
     * 院校地域
     */
    private Map<String, String> schoolAreaMap;

    /**
     * 院校属性
     */
    private Map<String, String> schoolAttributeMap;

}
