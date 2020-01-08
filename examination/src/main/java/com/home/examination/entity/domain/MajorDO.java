package com.home.examination.entity.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("major")
public class MajorDO extends BaseEntity {

    /**
     * 专业名称
     */
    String name;

    /**
     * 门类
     */
    String categoryType;
    /**
     * 专业类
     */
    String majorType;
    /**
     * 学科名称
     */
    String subjectName;
    /**
     * 学历
     */
    String education;
    /**
     * 学位
     */
    String academicDegree;
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

    // 新增字段
    /**
     * 评价星级
     */
    private String starRating;

    /**
     * 学校Id
     */
    private Long schoolId;
    /**
     * 招生人数
     */
    private Integer recruitNum;
    /**
     * 录取分数线
     */
    private Integer admissionScoreLine;



}
