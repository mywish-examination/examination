package com.home.examination.entity.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;

@Data
@TableName("school_major")
public class SchoolMajorDO extends BaseEntity {

    /**
     * 招生人数
     */
    private Integer recruitNum;
    /**
     * 录取分数线
     */
    private BigDecimal admissionScoreLine;
    /**
     * 学校Id
     */
    private String educationalCode;
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