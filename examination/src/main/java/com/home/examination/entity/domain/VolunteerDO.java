package com.home.examination.entity.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;

@Data
@TableName("volunteer")
public class VolunteerDO extends BaseEntity {

    /**
     * 志愿Id
     */
    Long majorId;
    /**
     * 用户Id
     */
    Long userId;
    /**
     * 学校Id
     */
    Long schoolId;
    /**
     * 分数
     */
    BigDecimal score;
    /**
     * 状态
     */
    String status;
}
