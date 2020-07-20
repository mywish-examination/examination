package com.home.examination.entity.domain;

import com.alibaba.druid.util.StringUtils;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.home.examination.common.enumerate.DictCodeEnum;
import com.home.examination.common.runner.MyStartupRunner;
import lombok.Data;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Data
@TableName("school")
public class SchoolDO extends BaseEntity {

    /**
     * 学校名称
     */
    private String name;
    /**
     * 学校主类型
     */
    private String mainType;

    public String getMainTypeName() {
        return DictCodeEnum.getValueByNum(DictCodeEnum.DICT_SCHOOL_TYPE.getCode(), this.mainType);
    }

    /**
     * 学校子类型
     */
    private String childrenType;
    @TableField(exist = false)
    private String[] childrenTypeArray;

    public String[] getChildrenTypeArray() {
        if (StringUtils.isEmpty(this.childrenType)) return null;
        return this.childrenType.split(",");
    }

    public String getChildrenTypeName() {
        if (StringUtils.isEmpty(this.childrenType)) return "";
        String[] split = this.childrenType.split(",");
        List<String> list = Arrays.asList(split);
        String result = list.stream().map(str ->
                DictCodeEnum.getValueByNum(DictCodeEnum.DICT_SCHOOL_TYPE.getCode(), str)
        ).collect(Collectors.joining(","));
        return result;
    }

    /**
     * 特色教育
     */
    private String featureEducational;
    @TableField(exist = false)
    private String[] featureEducationalArray;

    public String[] getFeatureEducationalArray() {
        if (StringUtils.isEmpty(this.featureEducational)) return null;
        return this.featureEducational.split(",");
    }

    public String getFeatureEducationalName() {
        if (StringUtils.isEmpty(this.featureEducational)) return "";
        String[] split = this.featureEducational.split(",");
        List<String> list = Arrays.asList(split);
        String result = list.stream().map(str ->
                DictCodeEnum.getValueByNum(DictCodeEnum.DICT_SCHOOL_FEATURE_EDUCATIONAL.getCode(), str)
        ).collect(Collectors.joining(","));
        return result;
    }

    /**
     * 曾用名
     */
    private String onceName;
    /**
     * 备注
     */
    private String remark;
    /**
     * 主管部门
     */
    private String mainManagerDepartment;

    public String getMainManagerDepartmentName() {
        return DictCodeEnum.getValueByNum(DictCodeEnum.DICT_SCHOOL_MAIN_MANAGER_DEPARTMENT.getCode(), this.mainManagerDepartment);
    }

    /**
     * 院校隶属
     */
    private String educationalInstitutionsSubjection;

    public String getEducationalInstitutionsSubjectionName() {
        return DictCodeEnum.getValueByNum(DictCodeEnum.DICT_SCHOOL_EDUCATIONAL_INSTITUTIONS_SUBJECTION.getCode(), this.educationalInstitutionsSubjection);
    }

    /**
     * 学历层次
     */
    private String educationLevel;

    public String getEducationLevelName() {
        return DictCodeEnum.getValueByNum(DictCodeEnum.DICT_SCHOOL_EDUCATION_LEVEL.getCode(), this.educationLevel);
    }

    /**
     * 院校官网链接
     */
    private String educationalInstitutionsWebsite;
    /**
     * 院校属性
     */
    private String educationalInstitutionsAttribute;
    @TableField(exist = false)
    private String[] educationalInstitutionsAttributeArray;

    public String[] getEducationalInstitutionsAttributeArray() {
        if (StringUtils.isEmpty(this.educationalInstitutionsAttribute)) return null;
        return this.educationalInstitutionsAttribute.split(",");
    }

    public String getEducationalInstitutionsAttributeName() {
        if (StringUtils.isEmpty(this.educationalInstitutionsAttribute)) return "";
        String[] split = this.educationalInstitutionsAttribute.split(",");
        List<String> list = Arrays.asList(split);
        String result = list.stream().map(str ->
                DictCodeEnum.getValueByNum(DictCodeEnum.DICT_SCHOOL_EDUCATIONAL_INSTITUTIONS_ATTRIBUTE.getCode(), str)
        ).collect(Collectors.joining(","));
        return result;
    }

    /**
     * 基本信息
     */
    private String baseInfo;
    /**
     * 院校招办链接
     */
    private String educationalInstitutionsRecruitUrl;
    /**
     * 招生章程链接
     */
    private String recruitConstitutionUrl;
    /**
     * 双一流学科
     */
    private String doubleFirstClassSubject;
    @TableField(exist = false)
    private String[] doubleFirstClassSubjectArray;

    public String[] getDoubleFirstClassSubjectArray() {
        if (StringUtils.isEmpty(this.doubleFirstClassSubject)) return null;
        return this.doubleFirstClassSubject.split(",");
    }

    public String getDoubleFirstClassSubjectName() {
        if (StringUtils.isEmpty(this.doubleFirstClassSubject)) return "";
        String[] split = this.doubleFirstClassSubject.split(",");
        List<String> list = Arrays.asList(split);
        String result = list.stream().map(str ->
                DictCodeEnum.getValueByNum(DictCodeEnum.DICT_SCHOOL_DOUBLE_FIRST_CLASS_SUBJECT.getCode(), str)
        ).collect(Collectors.joining(","));
        return result;
    }

    /**
     * 院校图标
     */
    private String educationalInstitutionsIconUrl;
    /**
     * 办学层次
     */
    private String schoolRunningLevel;

    public String getSchoolRunningLevelName() {
        return DictCodeEnum.getValueByNum(DictCodeEnum.DICT_SCHOOL_RUNNING_LEVEL.getCode(), this.schoolRunningLevel);
    }

    /**
     * 省份Id
     */
    private Long provinceId;
    /**
     * 省份名称
     */
    @TableField(exist = false)
    private String provinceName;

    public String getProvinceName() {
        if (this.provinceId == null) return "";
        List<CityDO> collect = MyStartupRunner.list.stream().filter(city -> city.getId().longValue() == this.provinceId.longValue()).collect(Collectors.toList());
        return collect.stream().map(CityDO::getCityName).findFirst().get();
    }

    // 新增字段
    /**
     * 评价星级
     */
    @TableField(exist = false)
    private String starRating;

    /**
     * 院校代码
     */
    private String educationalCode;

    /**
     * 专业列表
     */
    @TableField(exist = false)
    private List<MajorDO> majorList;

    @TableField(exist = false)
    private String batchCode;

    /**
     * 位次段落类
     */
    @TableField(exist = false)
    private RankParagraphDO rankParagraph;

    /**
     * 收藏状态，1=收藏，0=不收藏
     */
    @TableField(exist = false)
    String collectionStatus;

    /**
     * 招生人数
     */
    @TableField(exist = false)
    private Integer enrolment;

}
