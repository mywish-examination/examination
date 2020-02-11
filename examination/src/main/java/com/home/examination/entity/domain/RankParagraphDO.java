package com.home.examination.entity.domain;

import lombok.Data;

@Data
public class RankParagraphDO {

    /**
     * 最低位次段落
     */
    private String minimumRankParagraph;
    /**
     * 平均位次段落
     */
    private String avgRankParagraph;
    /**
     * 最高位次段落
     */
    private String highestRankParagraph;

    /**
     * 类型
     */
    private String type;

}
