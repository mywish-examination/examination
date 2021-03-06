package com.home.examination.entity.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;

@Data
@TableName("history_admission_data")
public class HistoryAdmissionDataDO extends BaseEntity {

    /**
     * 年份
     */
    private Integer years;
    /**
     * 最高分
     */
    private BigDecimal highestScore;

    public BigDecimal getHighestScore() {
        return highestScore.setScale(1, RoundingMode.HALF_UP);
    }

    /**
     * 最低分
     */
    private BigDecimal minimumScore;

    public BigDecimal getMinimumScore() {
        return minimumScore.setScale(1, RoundingMode.HALF_UP);
    }

    /**
     * 平均分
     */
    private BigDecimal average;

    public BigDecimal getAverage() {
        return average.setScale(1, RoundingMode.HALF_UP);
    }

    /**
     * 控制线
     */
    private BigDecimal controlLine;
    /**
     * 院校代码
     */
    private String educationalCode;
    /**
     * 专业Id
     */
    private Long majorId;

    /**
     * 专业名称
     */
    private String majorName;
    /**
     * 学校名称
     */
    @TableField(exist = false)
    private String schoolName;

    /**
     * 批次代码
     */
    private String batchCode;

    /**
     * 备注
     */
    private String remark;
    /**
     * 录取人数
     */
    private Integer enrolment;
    /**
     * 平均位次
     */
    private Integer avgRank;
    /**
     * 最高位次
     */
    private Integer highestRank;
    /**
     * 最低位次
     */
    private Integer minimumRank;
    /**
     * 科类代码
     */
    private String subjectCode;

    @TableField(exist = false)
    private String userSubjectType;

}
