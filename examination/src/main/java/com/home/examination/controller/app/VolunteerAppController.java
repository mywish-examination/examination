package com.home.examination.controller.app;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.home.examination.common.utils.ExUtils;
import com.home.examination.entity.domain.*;
import com.home.examination.entity.vo.AdmissionEstimateReferenceDO;
import com.home.examination.entity.vo.ExecuteResult;
import com.home.examination.entity.vo.VolunteerVO;
import com.home.examination.service.HistoryAdmissionDataService;
import com.home.examination.service.MajorService;
import com.home.examination.service.SchoolService;
import com.home.examination.service.VolunteerService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/app/volunteer")
public class VolunteerAppController {

    @Resource
    private VolunteerService volunteerService;
    @Resource
    private RedisTemplate redisTemplate;
    @Resource
    private SchoolService schoolService;
    @Resource
    private MajorService majorService;
    @Resource
    private HistoryAdmissionDataService historyAdmissionDataService;

    private final static BigDecimal zero = new BigDecimal(0);

    /**
     * 志愿档案
     *
     * @return
     */
    @PostMapping("/list")
    public ExecuteResult list(String token) {
        UserDO userDO = (UserDO) redisTemplate.opsForValue().get(token);
        LambdaQueryWrapper<VolunteerDO> volunteerQueryWrapper = new LambdaQueryWrapper<>();
        volunteerQueryWrapper.eq(VolunteerDO::getUserId, userDO.getId());
        List<VolunteerDO> list = volunteerService.list(volunteerQueryWrapper);

        int year = LocalDate.now().minusYears(3).getYear();
        if (list.isEmpty()) {
            return new ExecuteResult();
        }

        List<String> educationalCodeList = list.stream().map(VolunteerDO::getEducationalCode).collect(Collectors.toList());

        LambdaQueryWrapper<HistoryAdmissionDataDO> historyAdmissionDataQueryWrapper = new LambdaQueryWrapper<>();
        historyAdmissionDataQueryWrapper.eq(HistoryAdmissionDataDO::getYears, LocalDate.now().getYear()).in(HistoryAdmissionDataDO::getEducationalCode, educationalCodeList);
        List<HistoryAdmissionDataDO> historyAdmissionDataList = historyAdmissionDataService.list(historyAdmissionDataQueryWrapper);
        Map<String, List<HistoryAdmissionDataDO>> historyAdmissionDataMap = historyAdmissionDataList.stream().collect(Collectors.groupingBy(HistoryAdmissionDataDO::getEducationalCode));

        Map<String, List<VolunteerDO>> collect = list.stream().collect(Collectors.groupingBy(VolunteerDO::getEducationalCode));

        Map<String, List<MajorDO>> volunteerList = new HashMap<>();
        for (Map.Entry<String, List<VolunteerDO>> entry : collect.entrySet()) {
            List<Long> majorIdList = entry.getValue().stream().map(VolunteerDO::getMajorId).collect(Collectors.toList());
            LambdaQueryWrapper<MajorDO> majorQueryWrapper = new LambdaQueryWrapper<>();
            majorQueryWrapper.in(MajorDO::getId, majorIdList);
            List<MajorDO> majorList = majorService.list(majorQueryWrapper);
            volunteerList.put(entry.getKey(), majorList);
        }

        LambdaQueryWrapper<SchoolDO> schoolQueryWrapper = new LambdaQueryWrapper<>();
        schoolQueryWrapper.in(SchoolDO::getEducationalCode, educationalCodeList);
        List<SchoolDO> schoolList = schoolService.list(schoolQueryWrapper);

        schoolList.forEach(schoolDO -> {
            schoolDO.setMajorList(volunteerList.get(schoolDO.getEducationalCode()));
            schoolDO.setBatchCode(historyAdmissionDataMap.get(schoolDO.getEducationalCode()).get(0).getBatchCode());
        });

        Map<String, List<SchoolDO>> result = schoolList.stream().collect(Collectors.groupingBy(SchoolDO::getBatchCode));
        List<Object> resultList = new ArrayList<>();
        for (Map.Entry<String, List<SchoolDO>> entry : result.entrySet()) {
            for (SchoolDO schoolInside : entry.getValue()) {
                for (MajorDO majorDO : schoolInside.getMajorList()) {
                    LambdaQueryWrapper<HistoryAdmissionDataDO> historyQueryWrapper = new LambdaQueryWrapper<>();
                    historyQueryWrapper.eq(HistoryAdmissionDataDO::getEducationalCode, schoolInside.getEducationalCode()).eq(HistoryAdmissionDataDO::getMajorId, majorDO.getId());
                    // 历史录取数据列表
                    List<HistoryAdmissionDataDO> historyAdmissionDataInsideList = historyAdmissionDataService.list(historyQueryWrapper);

                    historyQueryWrapper.apply("years >= {0}", year);
                    AdmissionEstimateReferenceDO admissionEstimateReference = historyAdmissionDataService.getBySchoolOrMajor(historyQueryWrapper);

                    BigDecimal resultInside = historyAdmissionDataService.probabilityFilingHandler(historyAdmissionDataInsideList, userDO.getRank());
                    if (resultInside.compareTo(zero) == 0) {
                        admissionEstimateReference.setStarRating("0");
                    } else {
                        String starRating = ExUtils.starRatingHandler(resultInside);
                        if (starRating.equals("0")) {
                            Supplier<Boolean> supplier = ExUtils.getUserHalfStarSupplier(userDO, admissionEstimateReference.getScoreParagraph());
                            if (supplier.get()) starRating = "0.5";
                        }
                        majorDO.setStarRating(starRating);
                    }
                }
            }

            Map<String, Object> resultMap = new HashMap<>();
            resultMap.put("batchCode", entry.getKey());
            resultMap.put("schoolList", entry.getValue());
            resultList.add(resultMap);
        }

        return new ExecuteResult(resultList);
    }

    /**
     * 专业主导
     *
     * @return
     */
    @PostMapping("/listVolunteerLeading")
    public ExecuteResult listVolunteerLeading(String token) {
        UserDO userDO = (UserDO) redisTemplate.opsForValue().get(token);
        int year = LocalDate.now().minusYears(3).getYear();
        LambdaQueryWrapper<VolunteerDO> volunteerQueryWrapper = new LambdaQueryWrapper<>();
        volunteerQueryWrapper.eq(VolunteerDO::getUserId, userDO.getId());
        List<VolunteerDO> list = volunteerService.list(volunteerQueryWrapper);

        if (list.isEmpty()) {
            return new ExecuteResult();
        }

        List<String> schoolIdList = list.stream().map(VolunteerDO::getEducationalCode).collect(Collectors.toList());
        Map<String, List<VolunteerDO>> collect = list.stream().collect(Collectors.groupingBy(VolunteerDO::getEducationalCode));

        Map<String, List<MajorDO>> volunteerList = new HashMap<>();
        for (Map.Entry<String, List<VolunteerDO>> entry : collect.entrySet()) {
            List<Long> majorIdList = entry.getValue().stream().map(VolunteerDO::getMajorId).collect(Collectors.toList());
            LambdaQueryWrapper<MajorDO> majorQueryWrapper = new LambdaQueryWrapper<>();
            majorQueryWrapper.in(MajorDO::getId, majorIdList);
            List<MajorDO> majorList = majorService.list(majorQueryWrapper);
            volunteerList.put(entry.getKey(), majorList);
        }

        LambdaQueryWrapper<SchoolDO> schoolQueryWrapper = new LambdaQueryWrapper<>();
        schoolQueryWrapper.in(SchoolDO::getEducationalCode, schoolIdList);
        List<SchoolDO> schoolList = schoolService.list(schoolQueryWrapper);

        schoolList.forEach(schoolDO -> {
            List<MajorDO> majorList = volunteerList.get(schoolDO.getEducationalCode());
            for (MajorDO majorDO : majorList) {
                LambdaQueryWrapper<HistoryAdmissionDataDO> historyQueryWrapper = new LambdaQueryWrapper<>();
                historyQueryWrapper.eq(HistoryAdmissionDataDO::getEducationalCode, schoolDO.getEducationalCode()).eq(HistoryAdmissionDataDO::getMajorId, majorDO.getId());
                // 历史录取数据列表
                List<HistoryAdmissionDataDO> historyAdmissionDataList = historyAdmissionDataService.list(historyQueryWrapper);

                historyQueryWrapper.apply("years >= {0}", year);
                AdmissionEstimateReferenceDO admissionEstimateReference = historyAdmissionDataService.getBySchoolOrMajor(historyQueryWrapper);
                if(admissionEstimateReference == null) continue;

                BigDecimal result = historyAdmissionDataService.probabilityFilingHandler(historyAdmissionDataList, userDO.getRank());
                if (result.compareTo(zero) == 0) {
                    admissionEstimateReference.setStarRating("0");
                } else {
                    String starRating = ExUtils.starRatingHandler(result);
                    if (starRating.equals("0")) {
                        Supplier<Boolean> supplier = ExUtils.getUserHalfStarSupplier(userDO, admissionEstimateReference.getScoreParagraph());
                        if (supplier.get()) starRating = "0.5";
                    }
                    majorDO.setStarRating(starRating);
                }
            }
            schoolDO.setMajorList(majorList);
        });

        VolunteerVO volunteerVO = new VolunteerVO();
        volunteerVO.setSchoolList(schoolList);

        return new ExecuteResult(volunteerVO);
    }

    @PostMapping("/delete")
    public ExecuteResult delete(String educationalCode, Long majorId, String token) {
        UserDO userDO = (UserDO) redisTemplate.opsForValue().get(token);
        LambdaQueryWrapper<VolunteerDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(VolunteerDO::getMajorId, majorId).eq(VolunteerDO::getEducationalCode, educationalCode).eq(VolunteerDO::getUserId, userDO.getId());
        boolean result = volunteerService.remove(wrapper);
        return new ExecuteResult(result);
    }

    @GetMapping("/detail")
    public ExecuteResult detail(Long id) {
        return new ExecuteResult(volunteerService.getById(id));
    }

}
