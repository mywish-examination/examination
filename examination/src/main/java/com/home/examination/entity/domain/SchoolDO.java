package com.home.examination.entity.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.home.examination.common.enumerate.DictCodeEnum;
import com.home.examination.common.runner.MyStartupRunner;
import lombok.Data;

import java.util.List;

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
        return DictCodeEnum.getValueByNum(DictCodeEnum.DICT_SCHOOL_MAIN_TYPE.getCode(), this.mainType);
    }

    /**
     * 学校子类型
     */
    private String childrenType;

    public String getChildrenTypeName() {
        return DictCodeEnum.getValueByNum(DictCodeEnum.DICT_SCHOOL_CHILDREN_TYPE.getCode(), this.childrenType);
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

    public String getEducationalInstitutionsAttributeName() {
        return DictCodeEnum.getValueByNum(DictCodeEnum.DICT_SCHOOL_EDUCATIONAL_INSTITUTIONS_ATTRIBUTE.getCode(), this.educationalInstitutionsAttribute);
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
        return MyStartupRunner.list.stream().filter(city -> city.getId() == this.provinceId).map(CityDO::getCityName).findFirst().get();
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

}
