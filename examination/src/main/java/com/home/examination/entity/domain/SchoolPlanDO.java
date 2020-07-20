package com.home.examination.entity.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.home.examination.common.enumerate.DictCodeEnum;
import com.home.examination.common.runner.MyStartupRunner;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Data
@TableName("school_plan")
public class SchoolPlanDO extends BaseEntity {

    private String schoolName;

    /**
     * 院校代码
     */
    private String educationalCode;

    /**
     * 专业id
     */
    private Long majorId;

    /**
     * 专业名称
     */
    private String majorName;

    /**
     * 专业代号
     */
    private String majorCode;

    /**
     * 批次代码
     */
    private String batchCode;

    /**
     * 科类代码
     */
    private String subjectCode;

    /**
     * 学制
     */
    private String educationalSystem;

    /**
     * 计划数
     */
    private Integer planNum;

    /**
     * 收费标准
     */
    private String chargingStandard;

    /**
     * 院校地址
     */
    private String schoolAddress;

    /**
     * 省份id
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

    /**
     * 主管部门
     */
    private String mainManagerDepartment;

    public String getMainManagerDepartmentName() {
        return DictCodeEnum.getValueByNum(DictCodeEnum.DICT_SCHOOL_MAIN_MANAGER_DEPARTMENT.getCode(), this.mainManagerDepartment);
    }

    /**
     * 口试状态 1：是，0：否
     */
    private String oralExaminationStatus;

    /**
     * 院校属性
     */
    private String schoolAttribute;

    /**
     * 备注
     */
    private String remark;

}
