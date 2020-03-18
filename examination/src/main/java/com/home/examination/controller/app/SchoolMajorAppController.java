package com.home.examination.controller.app;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.home.examination.common.utils.ExUtils;
import com.home.examination.entity.domain.*;
import com.home.examination.entity.page.SchoolMajorPager;
import com.home.examination.entity.vo.AdmissionEstimateReferenceDO;
import com.home.examination.entity.vo.ExecuteResult;
import com.home.examination.service.*;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/app/schoolMajor")
public class SchoolMajorAppController {

    @Resource
    private MajorService majorService;
    @Resource
    private HistoryAdmissionDataService historyAdmissionDataService;
    @Resource
    private RedisTemplate redisTemplate;
    @Resource
    private MyCollectionService myCollectionService;
    @Resource
    private SchoolMajorService schoolMajorService;
    @Resource
    private SchoolService schoolService;

    @RequestMapping("/listPage")
    @ResponseBody
    public ExecuteResult listPage(SchoolMajorPager pager) {
        RangeDO rangeDO = pager.getRequestParam();
        LambdaQueryWrapper<MajorDO> queryWrapper = new LambdaQueryWrapper<>();

        LambdaQueryWrapper<SchoolDO> schoolQueryWrapper = new LambdaQueryWrapper<>();
        schoolQueryWrapper.in(!rangeDO.getProvinceIdList().isEmpty(), SchoolDO::getProvinceId, rangeDO.getProvinceIdList())
                .in(!rangeDO.getMainTypeList().isEmpty(), SchoolDO::getMainType, rangeDO.getMainTypeList())
                .in(!rangeDO.getChildrenTypeList().isEmpty(), SchoolDO::getChildrenType, rangeDO.getChildrenTypeList())
                .in(!rangeDO.getEducationalInstitutionsAttributeList().isEmpty(), SchoolDO::getEducationalInstitutionsAttribute, rangeDO.getEducationalInstitutionsAttributeList()).
                select(SchoolDO::getEducationalCode);
        List<SchoolDO> schoolList = schoolService.list(schoolQueryWrapper);

        queryWrapper.in(!rangeDO.getMajorNameList().isEmpty(), MajorDO::getName, rangeDO.getMajorNameList())
                .in(!rangeDO.getSubjectTypeList().isEmpty(), MajorDO::getSubjectType, rangeDO.getSubjectTypeList());
        List<MajorDO> majorList = majorService.list(queryWrapper);

        List<String> educationalCodeList = schoolList.stream().map(SchoolDO::getEducationalCode).collect(Collectors.toList());
        List<Long> majorIdList = majorList.stream().map(MajorDO::getId).collect(Collectors.toList());

        LambdaQueryWrapper<SchoolMajorDO> schoolMajorQueryWrapper = new LambdaQueryWrapper<>();
        String educationalCodeStr = educationalCodeList.stream().map(educationalCode -> "'" + educationalCode + "'").collect(Collectors.joining(","));
        schoolMajorQueryWrapper.select(SchoolMajorDO::getMajorId)
                .apply(!educationalCodeList.isEmpty(), "t.educational_code in (" + educationalCodeStr + ")")
                .in(!majorIdList.isEmpty(), SchoolMajorDO::getMajorId, majorIdList);
        int total = schoolMajorService.countByQueryWrapper(schoolMajorQueryWrapper);

        List<SchoolMajorDO> schoolMajorList = Collections.emptyList();
        if (total > 0) {
            schoolMajorQueryWrapper.last(String.format("limit %s, %s", pager.getPager().offset(), pager.getPager().getSize()));
            schoolMajorList = schoolMajorService.pageByQueryWrapper(schoolMajorQueryWrapper);

            UserDO user = (UserDO) redisTemplate.opsForValue().get(pager.getToken());
            int year = LocalDate.now().minusYears(3).getYear();

            LambdaQueryWrapper<MyCollectionDO> myCollectionQueryWrapper = new LambdaQueryWrapper<>();

            myCollectionQueryWrapper.in(MyCollectionDO::getMajorId, majorIdList).eq(MyCollectionDO::getUserId, user.getId());
            List<MyCollectionDO> myCollectionList = myCollectionService.list(myCollectionQueryWrapper);
            List<Long> existMyCollectionCollect = myCollectionList.stream().map(MyCollectionDO::getMajorId).collect(Collectors.toList());
            for (SchoolMajorDO schoolMajor :
                    schoolMajorList) {
                if (existMyCollectionCollect.contains(schoolMajor.getMajorId())) schoolMajor.setCollectionStatus("1");

                LambdaQueryWrapper<HistoryAdmissionDataDO> historyQueryWrapper = new LambdaQueryWrapper<>();
                historyQueryWrapper.eq(schoolMajor.getId() != null, HistoryAdmissionDataDO::getMajorId, schoolMajor.getId());
                // 历史录取数据列表
                List<HistoryAdmissionDataDO> historyAdmissionDataList = historyAdmissionDataService.list(historyQueryWrapper);

                historyQueryWrapper.apply("years >= {0}", year);
                AdmissionEstimateReferenceDO admissionEstimateReference = historyAdmissionDataService.getBySchoolOrMajor(historyQueryWrapper);
                if(admissionEstimateReference == null) continue;

                BigDecimal result = historyAdmissionDataService.probabilityFilingHandler(historyAdmissionDataList, user);
                BigDecimal zero = new BigDecimal(0);
                Supplier<Boolean> supplier = () -> {
                    BigDecimal score = user.getCollegeScore();
                    if (user.getCollegeScore() == null) {
                        score = user.getPredictedScore();
                    }

                    BigDecimal insideResult = score
                            .divide(new BigDecimal(admissionEstimateReference.getScoreParagraph().split("-")[0]), 2, RoundingMode.HALF_UP);
                    return insideResult.compareTo(new BigDecimal(15)) < 0;
                };
                schoolMajor.setStarRating(ExUtils.starRatingHandler(result, supplier));

                String collectionStatus = "";
                schoolMajor.setCollectionStatus(collectionStatus);
            }
        }

        pager.getPager().setTotal(total);
        pager.getPager().setRecords(schoolMajorList);

        return new ExecuteResult(pager);
    }

    @GetMapping("/detail")
    public ExecuteResult detail(Long id) {
        return new ExecuteResult(majorService.getById(id));
    }

}
