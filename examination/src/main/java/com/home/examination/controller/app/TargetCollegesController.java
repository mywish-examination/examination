package com.home.examination.controller.app;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.home.examination.common.utils.ExUtils;
import com.home.examination.entity.domain.*;
import com.home.examination.entity.vo.AdmissionEstimateReferenceDO;
import com.home.examination.entity.vo.ExecuteResult;
import com.home.examination.entity.vo.TargetCollegesVO;
import com.home.examination.service.*;
import org.springframework.beans.BeanUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
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
    private RedisTemplate redisTemplate;

    @PostMapping("/detail")
    public ExecuteResult detail(String educationalCode, Long majorId, String token) {
        TargetCollegesVO targetColleges = new TargetCollegesVO();

        SchoolDO school = schoolService.getByEducationalCode(educationalCode);
        UserDO userDO = (UserDO) redisTemplate.opsForValue().get(token);
        int year = LocalDate.now().minusYears(3).getYear();
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
            List<String> educationalCodeList = list.stream().map(SchoolDO::getEducationalCode).collect(Collectors.toList());
            myCollectionQueryWrapper.eq(MyCollectionDO::getMajorId, majorId).in(MyCollectionDO::getEducationalCode, educationalCodeList).eq(MyCollectionDO::getUserId, userDO.getId());
            List<MyCollectionDO> myCollectionList = myCollectionService.list(myCollectionQueryWrapper);
            List<String> existMyCollectionCollect = myCollectionList.stream().map(MyCollectionDO::getEducationalCode).collect(Collectors.toList());
            for(SchoolDO schoolDO: list) {
                if(existMyCollectionCollect.contains(schoolDO.getEducationalCode())) schoolDO.setCollectionStatus("1");
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
                        return new BigDecimal(userDO.getCollegeScore()).divide(new BigDecimal(minScore), 2, RoundingMode.HALF_UP).compareTo(new BigDecimal(15)) < 0;
                    };
                    major.setStarRating(ExUtils.starRatingHandler(result, supplier));
                }
                targetColleges.setMajorList(majorList.subList(0, majorList.size() >= 10 ? 10 : majorList.size()));
            }
        }

        LambdaQueryWrapper<HistoryAdmissionDataDO> historyQueryWrapper = new LambdaQueryWrapper<>();
        historyQueryWrapper.eq(HistoryAdmissionDataDO::getEducationalCode, educationalCode).eq(majorId != null, HistoryAdmissionDataDO::getMajorId, majorId);
        // 历史录取数据列表
        List<HistoryAdmissionDataDO> historyAdmissionDataList = historyAdmissionDataService.list(historyQueryWrapper);
        targetColleges.setHistoryAdmissionDataList(historyAdmissionDataList);

        historyQueryWrapper.apply("years >= {0}", year);
        AdmissionEstimateReferenceDO admissionEstimateReference = historyAdmissionDataService.getBySchoolOrMajor(historyQueryWrapper);
        if(admissionEstimateReference == null) return new ExecuteResult(targetColleges);

        BigDecimal result = historyAdmissionDataService.probabilityFilingHandler(historyAdmissionDataList, userDO);
        String probabilityFiling = result.multiply(new BigDecimal(100)).toString() + "%";
        admissionEstimateReference.setProbabilityFiling(probabilityFiling);

        Supplier<Boolean> supplier = () -> {
            String minScore = admissionEstimateReference.getScoreParagraph().split("-")[0];
            return new BigDecimal(userDO.getCollegeScore()).divide(new BigDecimal(minScore), 2, RoundingMode.HALF_UP).compareTo(new BigDecimal(15)) < 0;
        };
        admissionEstimateReference.setStarRating(ExUtils.starRatingHandler(result, supplier));

        targetColleges.setAdmissionEstimateReference(admissionEstimateReference);
        return new ExecuteResult(targetColleges);
    }

}
