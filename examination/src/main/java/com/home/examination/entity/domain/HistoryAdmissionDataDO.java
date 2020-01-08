package com.home.examination.entity.domain;

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

}
