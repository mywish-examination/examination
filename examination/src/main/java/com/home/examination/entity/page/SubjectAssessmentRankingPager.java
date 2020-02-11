package com.home.examination.entity.page;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.home.examination.entity.domain.SubjectAssessmentRankingDO;
import lombok.Data;
import org.springframework.stereotype.Component;

/**
 * 分页类
 */
@Data
@Component
public class SubjectAssessmentRankingPager {

    /**
     * 分页信息
     */
    private Page<SubjectAssessmentRankingDO> pager;
    /**
     * 查询参数
     */
    private SubjectAssessmentRankingDO requestParam;

    /**
     * token
     */
    private String token;

}