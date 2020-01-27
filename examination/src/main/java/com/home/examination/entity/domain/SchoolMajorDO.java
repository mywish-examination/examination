package com.home.examination.entity.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("school_major")
public class SchoolMajorDO extends BaseEntity {

    /**
     * 学科名称
     */
    String subjectName;
    /**
     * 招生人数
     */
    private Integer recruitNum;
    /**
     * 录取分数线
     */
    private Integer admissionScoreLine;
    /**
     * 评价星级
     */
    private String starRating;
    /**
     * 学校Id
     */
    private Long schoolId;
    /**
     * 专业Id
     */
    private Long majorId;

    /**
     * 学校名称
     */
    @TableField(exist = false)
    private String schoolName;

    /**
     * 专业名称
     */
    @TableField(exist = false)
    private String majorName;
}