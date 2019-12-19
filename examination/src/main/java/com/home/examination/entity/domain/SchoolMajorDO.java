package com.home.examination.entity.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("school_major")
public class SchoolMajorDO extends BaseEntity {

    /**
     * 学校Id
     */
    private Long schoolId;
    /**
     * 招生人数
     */
    private Integer recruitNum;
    /**
     * 专业Id
     */
    private Long majorId;
    /**
     * 录取分数线
     */
    private Integer admissionScoreLine;
}
