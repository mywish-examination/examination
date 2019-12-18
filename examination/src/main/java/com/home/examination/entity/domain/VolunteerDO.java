package com.home.examination.entity.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;

@Data
@TableName("volunteer")
public class VolunteerDO extends BaseEntity {

    /**
     * 状态
     */
    String status;
    /**
     * 志愿Id
     */
    Long major_id;
    /**
     * 用户Id
     */
    Long user_id;
    /**
     * 学校Id
     */
    Long school_id;
    /**
     * 分数
     */
    BigDecimal score;
}
