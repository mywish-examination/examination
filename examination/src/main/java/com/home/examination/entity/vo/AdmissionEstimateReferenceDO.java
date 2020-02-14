package com.home.examination.entity.vo;

import lombok.Data;

@Data
public class AdmissionEstimateReferenceDO {

    /**
     * 位次段落
     */
    private String rankParagraph;

    /**
     * 分数段落
     */
    private String scoreParagraph;

    /**
     * 投档概率
     */
    private String probabilityFiling;

    /**
     * 评估星级
     */
    private String starRating;

}
