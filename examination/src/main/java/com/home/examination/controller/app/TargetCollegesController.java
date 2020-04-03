package com.home.examination.controller.app;

import com.alibaba.druid.util.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.home.examination.common.utils.ExUtils;
import com.home.examination.entity.domain.*;
import com.home.examination.entity.vo.AdmissionEstimateReferenceDO;
import com.home.examination.entity.vo.ExecuteResult;
import com.home.examination.entity.vo.TargetCollegesVO;
import com.home.examination.service.*;
import org.springframework.beans.BeanUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/app/targetColleges")
public class TargetCollegesController {

    @Resource
    private MajorService majorService;
    @Resource
    private SchoolService schoolService;
    @Resource
    private HistoryAdmissionDataService historyAdmissionDataService;
    @Resource
    private SchoolMajorService schoolMajorService;
    @Resource
    private MyCollectionService myCollectionService;
    @Resource
    private SchoolPlanService schoolPlanService;

    @Resource
    private RedisTemplate redisTemplate;

    /**
     * @param historyAdmissionDataDO
     * @param token
     * @param sortType 1：星级，2：名称
     * @return
     */
    @PostMapping("/detail")
    public ExecuteResult detail(HistoryAdmissionDataDO historyAdmissionDataDO, String token, String sortType) {
        UserDO userDO = (UserDO) redisTemplate.opsForValue().get(token);
        TargetCollegesVO targetColleges = new TargetCollegesVO();
        BigDecimal zero = new BigDecimal(0);

        String educationalCode = historyAdmissionDataDO.getEducationalCode();
        SchoolDO school = schoolService.getByEducationalCode(educationalCode);
        int year = LocalDate.now().minusYears(3).getYear();
        Long majorId = historyAdmissionDataDO.getMajorId();
        if (majorId != null && educationalCode != null) {
            MajorDO one = majorService.getById(majorId);

            LambdaQueryWrapper<SchoolMajorDO> schoolMajorQueryWrapper = new LambdaQueryWrapper<>();
            schoolMajorQueryWrapper.eq(SchoolMajorDO::getMajorId, one.getId());
            List<SchoolMajorDO> schoolMajorList = schoolMajorService.list(schoolMajorQueryWrapper);
            List<String> collect = schoolMajorList.stream().map(SchoolMajorDO::getEducationalCode).collect(Collectors.toList());

            LambdaQueryWrapper<SchoolDO> schoolQueryWrapper = new LambdaQueryWrapper<>();
            schoolQueryWrapper.in(SchoolDO::getEducationalCode, collect);
            List<SchoolDO> list = schoolService.list(schoolQueryWrapper);

            LambdaQueryWrapper<MyCollectionDO> myCollectionQueryWrapper = new LambdaQueryWrapper<>();
            myCollectionQueryWrapper.eq(MyCollectionDO::getMajorId, majorId).in(MyCollectionDO::getEducationalCode, collect).eq(MyCollectionDO::getUserId, userDO.getId());
            List<MyCollectionDO> myCollectionList = myCollectionService.list(myCollectionQueryWrapper);
            List<String> existMyCollectionCollect = myCollectionList.stream().map(MyCollectionDO::getEducationalCode).collect(Collectors.toList());
            for(SchoolDO schoolDO: list) {
                if(existMyCollectionCollect.contains(schoolDO.getEducationalCode())) schoolDO.setCollectionStatus("1");

                LambdaQueryWrapper<SchoolPlanDO> schoolPlanQueryWrapper = new LambdaQueryWrapper<>();
                schoolPlanQueryWrapper.eq(SchoolPlanDO::getEducationalCode, schoolDO.getEducationalCode());
                List<SchoolPlanDO> schoolPlanList = schoolPlanService.list(schoolPlanQueryWrapper);
                if(!CollectionUtils.isEmpty(schoolPlanList)) {
                    schoolDO.setEnrolment(schoolPlanList.get(0).getPlanNum().toString());
                }

                LambdaQueryWrapper<HistoryAdmissionDataDO> historyQueryWrapper = new LambdaQueryWrapper<>();
                historyQueryWrapper.eq(HistoryAdmissionDataDO::getEducationalCode, schoolDO.getEducationalCode());
                // 历史录取数据列表
                List<HistoryAdmissionDataDO> historyAdmissionDataList = historyAdmissionDataService.list(historyQueryWrapper);

                historyQueryWrapper.apply("years >= {0}", year);
                AdmissionEstimateReferenceDO admissionEstimateReference = historyAdmissionDataService.getBySchoolOrMajor(historyQueryWrapper);

                BigDecimal result = historyAdmissionDataService.probabilityFilingHandler(historyAdmissionDataList, userDO);
                Supplier<Boolean> supplier = () -> {
                    BigDecimal score = userDO.getCollegeScore();
                    if (userDO.getCollegeScore() == null) {
                        score = userDO.getPredictedScore();
                    }

                    BigDecimal insideResult = score
                            .divide(new BigDecimal(admissionEstimateReference.getScoreParagraph().split("-")[0]), 2, RoundingMode.HALF_UP);
                    return insideResult.compareTo(new BigDecimal(15)) < 0;
                };
                schoolDO.setStarRating(ExUtils.starRatingHandler(result, supplier));
            }

            targetColleges.setSchoolList(list.subList(0, list.size() >= 10 ? 10 : list.size()));

            targetColleges.setSchoolName(school.getName());
            targetColleges.setMajorName(one.getName());

        } else if (majorId == null && educationalCode != null) {
            BeanUtils.copyProperties(school, targetColleges);
            targetColleges.setSchoolName(school.getName());

            LambdaQueryWrapper<SchoolMajorDO> schoolMajorQueryWrapper = new LambdaQueryWrapper<>();
            schoolMajorQueryWrapper.eq(SchoolMajorDO::getEducationalCode, educationalCode);
            List<SchoolMajorDO> schoolMajorList = schoolMajorService.list(schoolMajorQueryWrapper);
            List<Long> collect = schoolMajorList.stream().map(SchoolMajorDO::getMajorId).collect(Collectors.toList());

            if (!collect.isEmpty()) {
                LambdaQueryWrapper<MajorDO> majorQueryWrapper = new LambdaQueryWrapper<>();
                majorQueryWrapper.in(MajorDO::getId, collect);
                // 专业列表
                List<MajorDO> majorList = majorService.list(majorQueryWrapper);

                LambdaQueryWrapper<MyCollectionDO> myCollectionQueryWrapper = new LambdaQueryWrapper<>();
                List<Long> majorIdList = majorList.stream().map(MajorDO::getId).collect(Collectors.toList());
                myCollectionQueryWrapper.in(MyCollectionDO::getMajorId, majorIdList).eq(MyCollectionDO::getEducationalCode, educationalCode).eq(MyCollectionDO::getUserId, userDO.getId());
                List<MyCollectionDO> myCollectionList = myCollectionService.list(myCollectionQueryWrapper);
                List<Long> existMyCollectionCollect = myCollectionList.stream().map(MyCollectionDO::getMajorId).collect(Collectors.toList());

                for (MajorDO major:
                        majorList) {
                    if(existMyCollectionCollect.contains(major.getId())) major.setCollectionStatus("1");

                    LambdaQueryWrapper<SchoolPlanDO> schoolPlanQueryWrapper = new LambdaQueryWrapper<>();
                    schoolPlanQueryWrapper.eq(SchoolPlanDO::getEducationalCode, educationalCode).eq(SchoolPlanDO::getMajorId, major.getId());
                    List<SchoolPlanDO> schoolPlanList = schoolPlanService.list(schoolPlanQueryWrapper);
                    if(!CollectionUtils.isEmpty(schoolPlanList)) {
                        major.setEnrolment(schoolPlanList.get(0).getPlanNum().toString());
                    }

                    LambdaQueryWrapper<HistoryAdmissionDataDO> historyQueryWrapper = new LambdaQueryWrapper<>();
                    historyQueryWrapper.eq(HistoryAdmissionDataDO::getEducationalCode, educationalCode).eq(HistoryAdmissionDataDO::getMajorId, major.getId());
                    // 历史录取数据列表
                    List<HistoryAdmissionDataDO> historyAdmissionDataList = historyAdmissionDataService.list(historyQueryWrapper);

                    historyQueryWrapper.apply("years >= {0}", year);
                    AdmissionEstimateReferenceDO admissionEstimateReference = historyAdmissionDataService.getBySchoolOrMajor(historyQueryWrapper);
                    if(admissionEstimateReference == null) continue;

                    BigDecimal result = historyAdmissionDataService.probabilityFilingHandler(historyAdmissionDataList, userDO);
                    Supplier<Boolean> supplier = () -> {
                        String minScore = admissionEstimateReference.getScoreParagraph().split("-")[0];
                        BigDecimal score = userDO.getCollegeScore();
                        if (userDO.getCollegeScore() == null) {
                            score = userDO.getPredictedScore();
                        }

                        BigDecimal insideResult = score
                                .divide(new BigDecimal(minScore), 2, RoundingMode.HALF_UP);
                        return insideResult.compareTo(new BigDecimal(15)) < 0;
                    };
                    major.setStarRating(ExUtils.starRatingHandler(result, supplier));
                }
                targetColleges.setMajorList(majorList.subList(0, majorList.size() >= 10 ? 10 : majorList.size()));
            }
        }

        // 设置用户的学科类别
        historyAdmissionDataDO.setUserSubjectType(userDO.getSubjectType());

        // 历史录取数据列表
        List<HistoryAdmissionDataDO> historyAdmissionDataList = historyAdmissionDataService.listHistoryAdmissionDataGroupYears(historyAdmissionDataDO);
        targetColleges.setHistoryAdmissionDataList(historyAdmissionDataList);

        LambdaQueryWrapper<HistoryAdmissionDataDO> historyQueryWrapper = new LambdaQueryWrapper<>();
        historyQueryWrapper.eq(HistoryAdmissionDataDO::getEducationalCode, educationalCode).eq(majorId != null, HistoryAdmissionDataDO::getMajorId, majorId);

        historyQueryWrapper.apply("years >= {0}", year);
        AdmissionEstimateReferenceDO admissionEstimateReference = historyAdmissionDataService.getBySchoolOrMajor(historyQueryWrapper);
        if(admissionEstimateReference == null) return new ExecuteResult(targetColleges);

        BigDecimal result = historyAdmissionDataService.probabilityFilingHandler(historyAdmissionDataList, userDO);
        String probabilityFiling = result.multiply(new BigDecimal(100)).toString() + "%";
        admissionEstimateReference.setProbabilityFiling(probabilityFiling);

        Supplier<Boolean> supplier = () -> {
            String minScore = admissionEstimateReference.getScoreParagraph().split("-")[0];
            BigDecimal score;
            if (userDO.getCollegeScore() == null) {
                score = userDO.getPredictedScore();
            } else {
                score = zero;
            }

            BigDecimal insideResult = score
                    .divide(new BigDecimal(minScore), 2, RoundingMode.HALF_UP);
            return insideResult.compareTo(new BigDecimal(15)) < 0;
        };
        admissionEstimateReference.setStarRating(ExUtils.starRatingHandler(result, supplier));

        targetColleges.setAdmissionEstimateReference(admissionEstimateReference);

        // 处理排序、和计划招生数
        if(majorId != null) {
            if(StringUtils.isEmpty(sortType) || sortType.equals("1"))
                targetColleges.getSchoolList().sort(Comparator.comparing(SchoolDO::getStarRating));
            else
                targetColleges.getSchoolList().sort(Comparator.comparing(SchoolDO::getName));
        } else {
            if(StringUtils.isEmpty(sortType) || sortType.equals("1"))
                targetColleges.getMajorList().sort(Comparator.comparing(MajorDO::getStarRating));
            else
                targetColleges.getMajorList().sort(Comparator.comparing(MajorDO::getName));

        }

        return new ExecuteResult(targetColleges);
    }

}
