package com.home.examination.entity.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.home.examination.common.enumerate.DictCodeEnum;
import lombok.Data;

import java.math.BigDecimal;

@Data
@TableName("subsection")
public class SubsectionDO extends BaseEntity {

    /**
     * 分数
     */
    private BigDecimal score;
    /**
     * 人数
     */
    private Integer num;
    /**
     * 位次
     */
    private Integer rank;
    /**
     * 年份
     */
    private Integer year;
    /**
     * 学科类型
     */
    private String subjectType;

    public String getSubjectTypeName() {
        return DictCodeEnum.getValueByNum(DictCodeEnum.DICT_SUBJECT_TYPE.getCode(), this.subjectType);
    }

}
