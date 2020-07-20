package com.home.examination.entity.vo;

import com.home.examination.entity.domain.HistoryAdmissionDataDO;
import com.home.examination.entity.domain.MajorDO;
import com.home.examination.entity.domain.SchoolDO;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class TargetCollegesVO {

    /**
     * 学校名称
     */
    private String schoolName;
    /**
     * 专业名称
     */
    private String majorName;
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
     * 办学层次
     */
    private String schoolRunningLevel;
    /**
     * 省份
     */
    private String province;
    /**
     * 评价星级
     */
    private String starRating;
    /**
     * 专业列表
     */
    private List<MajorDO> majorList = new ArrayList<>();

    /**
     * 历年录取分数列表
     */
    private List<HistoryAdmissionDataDO> historyAdmissionDataList = new ArrayList<>();

    /**
     * 学校列表
     */
    private List<SchoolDO> schoolList = new ArrayList<>();

    /**
     * 录取预估参考
     */
    private AdmissionEstimateReferenceDO admissionEstimateReference = new AdmissionEstimateReferenceDO();

}
