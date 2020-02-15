package com.home.examination.entity.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.home.examination.common.enumerate.DictCodeEnum;
import com.home.examination.common.runner.MyStartupRunner;
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
    private String num;
    /**
     * 位次
     */
    private String rank;
    /**
     * 年份
     */
    private String year;
    /**
     * 学科类型
     */
    private String subjectType;

    public String getSubjectTypeName() {
        return DictCodeEnum.getValueByNum(DictCodeEnum.DICT_SUBJECT_TYPE.getCode(), this.subjectType);
    }

}
