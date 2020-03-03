package com.home.examination.entity.domain;

import com.alibaba.druid.util.StringUtils;
import lombok.Data;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Data
public class RangeDO {

    /**
     * 国家重点学科
     */
    private String subjectType;

    public List<String> getSubjectTypeList() {
        List<String> list = Collections.emptyList();
        if (StringUtils.isEmpty(this.subjectType)) return list;

        String[] array = this.subjectType.split(",");
        list = Arrays.asList(array);
        return list;
    }

    /**
     * 专业
     */
    private String majorName;
    public List<String> getMajorNameList() {
        List<String> list = Collections.emptyList();
        if (StringUtils.isEmpty(this.majorName)) return list;

        String[] array = this.majorName.split(",");
        list = Arrays.asList(array);
        return list;
    }
    /**
     * 省份、院校地域
     */
    private String provinceId;
    public List<String> getProvinceIdList() {
        List<String> list = Collections.emptyList();
        if (StringUtils.isEmpty(this.provinceId)) return list;

        String[] array = this.provinceId.split(",");
        list = Arrays.asList(array);
        return list;
    }
    /**
     * 主院校类别
     */
    private String mainType;
    public List<String> getMainTypeList() {
        List<String> list = Collections.emptyList();
        if (StringUtils.isEmpty(this.majorName)) return list;

        String[] array = this.majorName.split(",");
        list = Arrays.asList(array);
        return list;
    }
    /**
     * 子院校类别
     */
    private String childrenType;
    public List<String> getChildrenTypeList() {
        List<String> list = Collections.emptyList();
        if (StringUtils.isEmpty(this.childrenType)) return list;

        String[] array = this.childrenType.split(",");
        list = Arrays.asList(array);
        return list;
    }
    /**
     * 院校属性
     */
    private String educationalInstitutionsAttribute;
    public List<String> getEducationalInstitutionsAttributeList() {
        List<String> list = Collections.emptyList();
        if (StringUtils.isEmpty(this.educationalInstitutionsAttribute)) return list;

        String[] array = this.educationalInstitutionsAttribute.split(",");
        list = Arrays.asList(array);
        return list;
    }

}
