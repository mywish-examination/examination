package com.home.examination.controller.app;

import com.alibaba.druid.util.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.home.examination.common.utils.ExUtils;
import com.home.examination.entity.domain.HistoryAdmissionDataDO;
import com.home.examination.entity.domain.RankParagraphDO;
import com.home.examination.entity.domain.SchoolDO;
import com.home.examination.entity.domain.UserDO;
import com.home.examination.entity.page.SchoolPager;
import com.home.examination.entity.vo.AdmissionEstimateReferenceDO;
import com.home.examination.entity.vo.ExecuteResult;
import com.home.examination.service.HistoryAdmissionDataService;
import com.home.examination.service.SchoolService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.function.Supplier;

@RestController
@RequestMapping("/app/school")
public class SchoolAppController {

    @Resource
    private SchoolService schoolService;
    @Resource
    private HistoryAdmissionDataService historyAdmissionDataService;
    @Resource
    private RedisTemplate redisTemplate;

    @PostMapping("/listPage")
    public ExecuteResult listPage(SchoolPager pager) {
        UserDO user = (UserDO) redisTemplate.opsForValue().get(pager.getToken());
        Integer rank = Integer.valueOf(user.getRank());

        SchoolDO school = pager.getRequestParam();
        LambdaQueryWrapper<SchoolDO> queryWrapper = new LambdaQueryWrapper<>();
        if (school != null) {
            queryWrapper.apply(!StringUtils.isEmpty(school.getName()), " name like '%" + school.getName() + "%'");
        }
        IPage<SchoolDO> page = schoolService.page(pager.getPager(), queryWrapper);

        int year = LocalDate.now().minusYears(3).getYear();
        for (SchoolDO schoolDO : page.getRecords()) {
            RankParagraphDO rankParagraph = historyAdmissionDataService.getRankParagraphBySchool(school.getEducationalCode(), String.valueOf(year));
            Integer minimumMin = Integer.valueOf(rankParagraph.getMinimumRankParagraph().split("-")[0]);
            Integer minimumMax = Integer.valueOf(rankParagraph.getMinimumRankParagraph().split("-")[1]);
            if (minimumMin >= rank && rank <= minimumMax) {
                rankParagraph.setType("0");
            }
            Integer avgMin = Integer.valueOf(rankParagraph.getAvgRankParagraph().split("-")[0]);
            Integer avgMax = Integer.valueOf(rankParagraph.getAvgRankParagraph().split("-")[1]);
            if (avgMin >= rank && rank <= avgMax) {
                rankParagraph.setType("1");
            }
            Integer highestMin = Integer.valueOf(rankParagraph.getHighestRankParagraph().split("-")[0]);
            Integer highestMax = Integer.valueOf(rankParagraph.getHighestRankParagraph().split("-")[1]);
            if (highestMin >= rank && rank <= highestMax) {
                rankParagraph.setType("2");
            }

            schoolDO.setRankParagraph(rankParagraph);

            LambdaQueryWrapper<HistoryAdmissionDataDO> historyQueryWrapper = new LambdaQueryWrapper<>();
            historyQueryWrapper.eq(HistoryAdmissionDataDO::getEducationalCode, schoolDO.getEducationalCode());
            // 历史录取数据列表
            List<HistoryAdmissionDataDO> historyAdmissionDataList = historyAdmissionDataService.list(historyQueryWrapper);

            historyQueryWrapper.apply("years >= {0}", year);
            AdmissionEstimateReferenceDO admissionEstimateReference = historyAdmissionDataService.getBySchoolOrMajor(historyQueryWrapper);

            BigDecimal result = historyAdmissionDataService.probabilityFilingHandler(historyAdmissionDataList, user);
            Supplier<Boolean> supplier = () -> new BigDecimal(user.getCollegeScore()).divide(new BigDecimal(admissionEstimateReference.getScoreParagraph().split("-")[0])).compareTo(new BigDecimal(15)) < 0;
            schoolDO.setStarRating(ExUtils.starRatingHandler(result, supplier));
        }

        return new ExecuteResult(page);
    }

}
