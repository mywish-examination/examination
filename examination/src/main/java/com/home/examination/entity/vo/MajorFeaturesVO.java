package com.home.examination.entity.vo;

import lombok.Data;

import java.util.Map;

@Data
public class MajorFeaturesVO {


    /**
     * 国家重点学科
     */
    private Map<String, String> nationalKeyDisciplineMap;
    /**
     * 男生热选专业
     */
    private Map<String, String> boyHotMajorMap;
    /**
     * 女生热选专业
     */
    private Map<String, String> girlHotMajorMap;
    /**
     * 大众热选专业
     */
    private Map<String, String> publicHotMajorMap;
    /**
     * 理工热选专业
     */
    private Map<String, String> scienceHotMajorMap;
    /**
     * 文史热选专业
     */
    private Map<String, String> literaryHistoryHotMajorMap;

}
