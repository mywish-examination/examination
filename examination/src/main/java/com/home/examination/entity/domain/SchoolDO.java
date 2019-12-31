package com.home.examination.entity.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

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
    /**
     * 学校子类型
     */
    private String childrenType;
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
    /**
     * 院校隶属
     */
    private String educationalInstitutionsSubjection;
    /**
     * 学历层次
     */
    private String educationLevel;
    /**
     * 院校官网链接
     */
    private String educationalInstitutionsWebsite;
    /**
     * 院校属性
     */
    private String educationalInstitutionsAttribute;
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
     * 半血层次
     */
    private String schoolRunningLevel;
    /**
     * 省份
     */
    private String province;

    // 新增字段
    /**
     * 评价星级
     */
    private String starRating;

}
