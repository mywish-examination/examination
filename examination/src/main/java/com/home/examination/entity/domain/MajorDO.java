package com.home.examination.entity.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.home.examination.common.enumerate.DictCodeEnum;
import lombok.Data;

@Data
@TableName("major")
public class MajorDO extends BaseEntity {

    /**
     * 专业名称
     */
    private String name;
    /**
     * 学科名称
     */
    private String subjectType;

    public String getSubjectTypeName() {
        return DictCodeEnum.getValueByNum(DictCodeEnum.DICT_MAJOR_CATEGORY_TYPE.getCode(), this.subjectType);
    }

    /**
     * 门类
     */
    private String categoryType;

    public String getCategoryTypeName() {
        return DictCodeEnum.getValueByNum(DictCodeEnum.DICT_MAJOR_CATEGORY_TYPE.getCode(), this.categoryType);
    }

    /**
     * 专业类
     */
    private String majorType;

    public String getMajorTypeName() {
        return DictCodeEnum.getValueByNum(DictCodeEnum.DICT_MAJOR_MAJOR_TYPE.getCode(), this.majorType);
    }

    /**
     * 学历
     */
    private String education;

    public String getEducationName() {
        return DictCodeEnum.getValueByNum(DictCodeEnum.DICT_MAJOR_EDUCATION.getCode(), this.education);
    }

    /**
     * 学位
     */
    private String academicDegree;

    public String getAcademicDegreeName() {
        return DictCodeEnum.getValueByNum(DictCodeEnum.DICT_MAJOR_ACADEMIC_DEGREE.getCode(), this.academicDegree);
    }

    /**
     * 就业率
     */
    private String employmentRate;
    /**
     * 年限
     */
    private String years;
    /**
     * 专业介绍
     */
    private String majorIntroduce;
    /**
     * 主要课程
     */
    private String mainCourse;
    /**
     * 就业方向
     */
    private String employmentDirection;
    /**
     * 向阳指导
     */
    private String toSunGuidance;

    /**
     * 收藏状态，1=收藏，0=不收藏
     */
    @TableField(exist = false)
    String collectionStatus;

}
