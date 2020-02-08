package com.home.examination.entity.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("subject_assessment_ranking")
public class SubjectAssessmentRankingDO extends BaseEntity {

    /**
     * 学科门类code
     */
    private String categoryType;
    /**
     * 学科门类名称
     */
    private String categoryName;
    /**
     * 学科code
     */
    private String subjectType;
    /**
     * 学科名称
     */
    private String subjectName;
    /**
     * 等级
     */
    private String level;
    /**
     * 院校名称
     */
    private String schoolName;
    /**
     * 全国排名
     */
    private String nationalRankings;
    /**
     * 档次
     */
    private String grade;

}
