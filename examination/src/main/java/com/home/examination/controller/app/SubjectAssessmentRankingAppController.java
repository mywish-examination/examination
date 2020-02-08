package com.home.examination.controller.app;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.home.examination.entity.domain.SubjectAssessmentRankingDO;
import com.home.examination.entity.page.SubjectAssessmentRankingPager;
import com.home.examination.entity.vo.ExecuteResult;
import com.home.examination.service.SubjectAssessmentRankingService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

@Controller
@RequestMapping("/app/subjectAssessmentRanking")
public class SubjectAssessmentRankingAppController {

    @Resource
    private SubjectAssessmentRankingService subjectAssessmentRankingService;

    @PostMapping("/listPage")
    @ResponseBody
    public ExecuteResult listPage(SubjectAssessmentRankingPager pager) {
        LambdaQueryWrapper<SubjectAssessmentRankingDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SubjectAssessmentRankingDO::getCategoryType, pager.getRequestParam().getCategoryType()).
                eq(SubjectAssessmentRankingDO::getSubjectType, pager.getRequestParam().getSubjectType());
        subjectAssessmentRankingService.page(pager.getPager(), queryWrapper);
        return new ExecuteResult(pager);
    }

}
