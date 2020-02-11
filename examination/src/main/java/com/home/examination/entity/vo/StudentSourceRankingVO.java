package com.home.examination.entity.vo;

import lombok.Data;

/**
 * 生源排名
 */
@Data
public class StudentSourceRankingVO {

    /**
     * 专业名称
     */
    private String majorName;
    /**
     * 位次
     */
    private Integer rank;
}
