package com.home.examination.controller.app;

import com.alibaba.druid.util.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.home.examination.common.utils.ExUtils;
import com.home.examination.entity.domain.*;
import com.home.examination.entity.page.SchoolPager;
import com.home.examination.entity.vo.AdmissionEstimateReferenceDO;
import com.home.examination.entity.vo.ByNameVO;
import com.home.examination.entity.vo.ExecuteResult;
import com.home.examination.service.HistoryAdmissionDataService;
import com.home.examination.service.MajorService;
import com.home.examination.service.SchoolMajorService;
import com.home.examination.service.SchoolService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/app/school")
public class SchoolAppController {

    @Resource
    private SchoolService schoolService;
    @Resource
    private HistoryAdmissionDataService historyAdmissionDataService;
    @Resource
    private MajorService majorService;
    @Resource
    private SchoolMajorService schoolMajorService;

    @Resource
    private RedisTemplate redisTemplate;

    private final static BigDecimal zero = new BigDecimal(0);

    @PostMapping("/listPage")
    public ExecuteResult listPage(SchoolPager pager) {
        UserDO userDO = (UserDO) redisTemplate.opsForValue().get(pager.getToken());
        Integer rank = Integer.valueOf(userDO.getRank());
        LambdaQueryWrapper<SchoolDO> queryWrapper = new LambdaQueryWrapper<>();
        RangeDO rangeDO = pager.getRequestParam();
        if(rangeDO == null) rangeDO = new RangeDO();

        RangeDO finalRangeDO = rangeDO;

        LambdaQueryWrapper<MajorDO> majorQueryWrapper = new LambdaQueryWrapper<>();
        majorQueryWrapper.in(!rangeDO.getMajorNameList().isEmpty(), MajorDO::getName, rangeDO.getMajorNameList())
                .in(!rangeDO.getSubjectTypeList().isEmpty(), MajorDO::getSubjectType, rangeDO.getSubjectTypeList()).select(MajorDO::getId);
        List<MajorDO> list = majorService.list(majorQueryWrapper);
        if (!list.isEmpty()) {
            List<Long> majorIdList = list.stream().map(MajorDO::getId).collect(Collectors.toList());

            LambdaQueryWrapper<SchoolMajorDO> schoolMajorQueryWrapper = new LambdaQueryWrapper<>();
            schoolMajorQueryWrapper.select(SchoolMajorDO::getEducationalCode).in(!majorIdList.isEmpty(), SchoolMajorDO::getMajorId, majorIdList);
            List<SchoolMajorDO> schoolMajorList = schoolMajorService.list(schoolMajorQueryWrapper);
            List<String> educationalCodeList = schoolMajorList.stream().map(SchoolMajorDO::getEducationalCode).distinct().collect(Collectors.toList());
            queryWrapper.in(!educationalCodeList.isEmpty(), SchoolDO::getEducationalCode, educationalCodeList);
        }

        queryWrapper.in(!rangeDO.getProvinceIdList().isEmpty(), SchoolDO::getProvinceId, rangeDO.getProvinceIdList())
                .and(!finalRangeDO.getMainTypeList().isEmpty() || !finalRangeDO.getChildrenTypeList().isEmpty(),wq -> wq.in(!finalRangeDO.getMainTypeList().isEmpty(), SchoolDO::getMainType, finalRangeDO.getMainTypeList())
                        .or().in(!finalRangeDO.getChildrenTypeList().isEmpty(), SchoolDO::getChildrenType, finalRangeDO.getChildrenTypeList()))
                .in(!rangeDO.getEducationalInstitutionsAttributeList().isEmpty(), SchoolDO::getEducationalInstitutionsAttribute, rangeDO.getEducationalInstitutionsAttributeList());
        IPage<SchoolDO> page = schoolService.page(pager.getPager(), queryWrapper);

        int year = LocalDate.now().minusYears(3).getYear();
        for (SchoolDO schoolDO : page.getRecords()) {
            RankParagraphDO rankParagraph = historyAdmissionDataService.getRankParagraphBySchool(schoolDO.getEducationalCode(), String.valueOf(year));
            if (rankParagraph == null) continue;

            Integer minimumMin = Integer.valueOf(Double.valueOf(rankParagraph.getMinimumRankParagraph().split("-")[0]).intValue());
            Integer minimumMax = Integer.valueOf(Double.valueOf(rankParagraph.getMinimumRankParagraph().split("-")[1]).intValue());
            if (minimumMin >= rank && rank <= minimumMax) {
                rankParagraph.setType("0");
            }
            Integer avgMin = Integer.valueOf(Double.valueOf(rankParagraph.getAvgRankParagraph().split("-")[0]).intValue());
            Integer avgMax = Integer.valueOf(Double.valueOf(rankParagraph.getAvgRankParagraph().split("-")[1]).intValue());
            if (avgMin >= rank && rank <= avgMax) {
                rankParagraph.setType("1");
            }
            Integer highestMin = Integer.valueOf(Double.valueOf(rankParagraph.getHighestRankParagraph().split("-")[0]).intValue());
            Integer highestMax = Integer.valueOf(Double.valueOf(rankParagraph.getHighestRankParagraph().split("-")[1]).intValue());
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

            BigDecimal result = historyAdmissionDataService.probabilityFilingHandler(historyAdmissionDataList, userDO.getRank());
            if (result.compareTo(zero) == 0) {
                admissionEstimateReference.setStarRating("0");
            } else {
                String starRating = ExUtils.starRatingHandler(result);
                if (starRating.equals("0")) {
                    Supplier<Boolean> supplier = ExUtils.getUserHalfStarSupplier(userDO, admissionEstimateReference.getScoreParagraph());
                    if (supplier.get()) starRating = "0.5";
                }
                schoolDO.setStarRating(starRating);
            }
        }

        return new ExecuteResult(page);
    }

    /**
     * 通过学院名称获取列表
     *
     * @param name
     * @return
     */
    @PostMapping("/lisByName")
    public ExecuteResult listPage(String name) {
        LambdaQueryWrapper<SchoolDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.apply(!StringUtils.isEmpty(name), " t.name like '%" + name + "%'");
        List<ByNameVO> list = schoolService.listByName(queryWrapper);

        return new ExecuteResult(list);
    }

}
