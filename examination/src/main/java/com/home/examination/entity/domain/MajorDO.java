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
    String name;
    /**
     * 学科名称
     */
    String subjectType;
    public String getSubjectTypeName() {
        return DictCodeEnum.getValueById(DictCodeEnum.DICT_MAJOR_CATEGORY_TYPE.getCode(), this.subjectType);
    }
    /**
     * 门类
     */
    String categoryType;
    public String getCategoryTypeName() {
        return DictCodeEnum.getValueById(DictCodeEnum.DICT_MAJOR_CATEGORY_TYPE.getCode(), this.categoryType);
    }
    /**
     * 专业类
     */
    String majorType;
    public String getMajorTypeName() {
        return DictCodeEnum.getValueById(DictCodeEnum.DICT_MAJOR_MAJOR_TYPE.getCode(), this.majorType);
    }
    /**
     * 学历
     */
    String education;
    public String getEducationName() {
        return DictCodeEnum.getValueById(DictCodeEnum.DICT_MAJOR_EDUCATION.getCode(), this.education);
    }
    /**
     * 学位
     */
    String academicDegree;
    public String getAcademicDegreeName() {
        return DictCodeEnum.getValueById(DictCodeEnum.DICT_MAJOR_ACADEMIC_DEGREE.getCode(), this.academicDegree);
    }
    /**
     * 就业率
     */
    String employmentRate;
    /**
     * 年限
     */
    String years;
    /**
     * 专业介绍
     */
    String majorIntroduce;
    /**
     * 主要课程
     */
    String mainCourse;
    /**
     * 就业方向
     */
    String employmentDirection;
    /**
     * 向阳指导
     */
    String toSunGuidance;

    /**
     * 收藏状态，1=收藏，0=不收藏
     */
    @TableField(exist = false)
    String collectionStatus;

}
