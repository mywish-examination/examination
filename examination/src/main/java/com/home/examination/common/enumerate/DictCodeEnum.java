package com.home.examination.common.enumerate;

import com.alibaba.druid.util.StringUtils;
import com.home.examination.common.runner.MyStartupRunner;
import com.home.examination.entity.domain.DataDictionaryDO;

public enum DictCodeEnum {

    // 学校字典Code
    DICT_SCHOOL_MAIN_TYPE("dict_school_main_type", "学校主类型"),
    DICT_SCHOOL_CHILDREN_TYPE("dict_school_children_type", "学校子类型"),
    DICT_SCHOOL_EDUCATION_LEVEL("dict_school_education_level", "学历层次"),
    DICT_SCHOOL_EDUCATIONAL_INSTITUTIONS_ATTRIBUTE("dict_school_educational_institutions_attribute", "院校属性"),
    DICT_SCHOOL_MAIN_MANAGER_DEPARTMENT("dict_school_main_manager_department", "主管部门"),
    DICT_SCHOOL_EDUCATIONAL_INSTITUTIONS_SUBJECTION("dict_school_educational_institutions_subjection", "院校隶属"),
    DICT_SCHOOL_RUNNING_LEVEL("dict_school_school_running_level", "办学层次"),

    // 用户字典Code
    DICT_USER_NATION("dict_user_nation", "民族"),
    DICT_USER_SUBJECT_TYPE("dict_user_subject_type", "科类"),
    DICT_USER_TYPE("dict_user_type", "类型"),
    DICT_USER_SEX("dict_user_sex", "性别"),

    // 专业字典Code
    DICT_MAJOR_CATEGORY_TYPE("dict_major_category_type", "门类"),
    DICT_MAJOR_MAJOR_TYPE("dict_major_major_type", "专业类"),
    DICT_MAJOR_EDUCATION("dict_major_education", "学历"),
    DICT_MAJOR_ACADEMIC_DEGREE("dict_major_academic_degree", "学位"),

    // 志愿档案Code
    DICT_VOLUNTEER_STATUS("dict_volunteer_status", "状态"),

    //public
    DICT_SUBJECT_TYPE("dict_subject_type", "学科"),
    DICT_FEATURE_TYPE("dict_feature_type", "特征类型"),
    ;

    private String code;
    private String message;

    DictCodeEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    /**
     * 通过数据字典Code和字典id获取字典值
     *
     * @param code
     * @param num
     * @return
     */
    public static String getValueByNum(String code, String num) {
        if (StringUtils.isEmpty(num)) return "";
        return MyStartupRunner.map.get(code).stream().filter(dataDictionaryDO -> dataDictionaryDO.getDictNum().equals(num)).map(DataDictionaryDO::getDictValue).findFirst().orElse("");
    }

    /**
     * 通过数据字典Code和字典value获取id
     *
     * @param code
     * @param value
     * @return
     */
    public static String getNumByValue(String code, String value) {
        if (StringUtils.isEmpty(value)) return "";
        return MyStartupRunner.map.get(code).stream().filter(dataDictionaryDO -> dataDictionaryDO.getDictValue().equals(value)).map(DataDictionaryDO::getDictNum).findFirst().get();
    }

}
