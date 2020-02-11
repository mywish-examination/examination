package com.home.examination.controller.app;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.home.examination.entity.domain.MajorDO;
import com.home.examination.entity.domain.SubjectAssessmentRankingDO;
import com.home.examination.entity.vo.ExecuteResult;
import com.home.examination.service.MajorService;
import com.home.examination.service.SubjectAssessmentRankingService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/app/subjectAssessmentRanking")
public class SubjectAssessmentRankingAppController {

    @Resource
    private SubjectAssessmentRankingService subjectAssessmentRankingService;
    @Resource
    private MajorService majorService;

    /**
     * 学科评估排名列表
     *
     * @return
     */
    @PostMapping("/list")
    @ResponseBody
    public ExecuteResult list(Long majorId, String educationalCode) {
        LambdaQueryWrapper<SubjectAssessmentRankingDO> queryWrapper = new LambdaQueryWrapper<>();
        if (majorId != null) {
            MajorDO major = majorService.getById(majorId);
            queryWrapper.eq(SubjectAssessmentRankingDO::getCategoryType, major.getCategoryType())
                    .eq(SubjectAssessmentRankingDO::getSubjectType, major.getSubjectType()).orderByAsc(SubjectAssessmentRankingDO::getId);

            List<SubjectAssessmentRankingDO> list = subjectAssessmentRankingService.list(queryWrapper);
            return new ExecuteResult(list);
        }
        if (educationalCode != null) {
            queryWrapper.eq(SubjectAssessmentRankingDO::getEducationalCode, educationalCode);
            List<SubjectAssessmentRankingDO> list = subjectAssessmentRankingService.list(queryWrapper);
            Map<String, List<SubjectAssessmentRankingDO>> collect = list.stream().collect(Collectors.groupingBy(SubjectAssessmentRankingDO::getCategoryType));
            return new ExecuteResult(collect);
        }
        return new ExecuteResult();
    }

}
