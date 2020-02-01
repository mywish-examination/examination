package com.home.examination.entity.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;

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
    /**
     * 最低分
     */
    private BigDecimal minimumScore;
    /**
     * 平均分
     */
    private BigDecimal average;
    /**
     * 控制线
     */
    private BigDecimal controlLine;
    /**
     * 学校Id
     */
    private Long schoolId;
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
    private String enrolment;
    /**
     * 平均位次
     */
    private String avgRank;
    /**
     * 最高位次
     */
    private String highestRank;
    /**
     * 最低位次
     */
    private String minimumRank;
    /**
     * 科类代码
     */
    private String subjectCode;

}
