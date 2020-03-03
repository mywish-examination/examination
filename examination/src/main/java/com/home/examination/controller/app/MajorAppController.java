package com.home.examination.controller.app;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.home.examination.common.utils.ExUtils;
import com.home.examination.entity.domain.*;
import com.home.examination.entity.page.MajorPager;
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
@RequestMapping("/app/major")
public class MajorAppController {

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
    public ExecuteResult listPage(MajorPager pager) {
        RangeDO rangeDO = pager.getRequestParam();
        LambdaQueryWrapper<MajorDO> queryWrapper = new LambdaQueryWrapper<>();

        LambdaQueryWrapper<SchoolDO> schoolQueryWrapper = new LambdaQueryWrapper<>();
        schoolQueryWrapper.in(!rangeDO.getProvinceIdList().isEmpty(), SchoolDO::getProvinceId, rangeDO.getProvinceIdList())
                .in(!rangeDO.getMainTypeList().isEmpty(), SchoolDO::getMainType, rangeDO.getMainTypeList())
                .in(!rangeDO.getChildrenType().isEmpty(), SchoolDO::getChildrenType, rangeDO.getChildrenTypeList())
                .in(!rangeDO.getEducationalInstitutionsAttributeList().isEmpty(), SchoolDO::getEducationalInstitutionsAttribute, rangeDO.getEducationalInstitutionsAttributeList()).
                select(SchoolDO::getEducationalCode);
        List<SchoolDO> schoolList = schoolService.list(schoolQueryWrapper);
        if(!schoolList.isEmpty()) {
            List<String> educationalCodeList = schoolList.stream().map(SchoolDO::getEducationalCode).collect(Collectors.toList());

            LambdaQueryWrapper<SchoolMajorDO> schoolMajorQueryWrapper = new LambdaQueryWrapper<>();
            schoolMajorQueryWrapper.select(SchoolMajorDO::getMajorId).in(!educationalCodeList.isEmpty(), SchoolMajorDO::getEducationalCode, educationalCodeList);
            List<SchoolMajorDO> schoolMajorList = schoolMajorService.list(schoolMajorQueryWrapper);
            List<Long> majorIdList = schoolMajorList.stream().map(SchoolMajorDO::getMajorId).collect(Collectors.toList());
            queryWrapper.in(!majorIdList.isEmpty(), MajorDO::getId, majorIdList);
        }

        queryWrapper.in(!rangeDO.getMajorNameList().isEmpty(), MajorDO::getName, rangeDO.getMajorNameList())
                .in(!rangeDO.getSubjectTypeList().isEmpty(), MajorDO::getSubjectType, rangeDO.getSubjectTypeList());

        int total = majorService.countByQueryWrapper(queryWrapper);
        List<MajorDO> list = Collections.emptyList();
        if (total > 0) {
            queryWrapper.last(String.format("limit %s, %s", pager.getPager().offset(), pager.getPager().getSize()));
            list = majorService.pageByQueryWrapper(queryWrapper);
        }

        UserDO user = (UserDO) redisTemplate.opsForValue().get(pager.getToken());
        int year = LocalDate.now().minusYears(3).getYear();

        LambdaQueryWrapper<MyCollectionDO> myCollectionQueryWrapper = new LambdaQueryWrapper<>();
        List<Long> majorIdList = list.stream().map(MajorDO::getId).collect(Collectors.toList());
        myCollectionQueryWrapper.in(MyCollectionDO::getMajorId, majorIdList).eq(MyCollectionDO::getUserId, user.getId());
        List<MyCollectionDO> myCollectionList = myCollectionService.list(myCollectionQueryWrapper);
        List<Long> existMyCollectionCollect = myCollectionList.stream().map(MyCollectionDO::getMajorId).collect(Collectors.toList());
        for (MajorDO major :
                list) {
            if (existMyCollectionCollect.contains(major.getId())) major.setCollectionStatus("1");

            LambdaQueryWrapper<HistoryAdmissionDataDO> historyQueryWrapper = new LambdaQueryWrapper<>();
            historyQueryWrapper.eq(major.getId() != null, HistoryAdmissionDataDO::getMajorId, major.getId());
            // 历史录取数据列表
            List<HistoryAdmissionDataDO> historyAdmissionDataList = historyAdmissionDataService.list(historyQueryWrapper);

            historyQueryWrapper.apply("years >= {0}", year);
            AdmissionEstimateReferenceDO admissionEstimateReference = historyAdmissionDataService.getBySchoolOrMajor(historyQueryWrapper);

            BigDecimal result = historyAdmissionDataService.probabilityFilingHandler(historyAdmissionDataList, user);
            BigDecimal zero = new BigDecimal(0);
            Supplier<Boolean> supplier = () -> {
                BigDecimal score;
                if (user.getCollegeScore() == null) {
                    score = user.getPredictedScore();
                } else {
                    score = zero;
                }

                BigDecimal insideResult = score
                        .divide(new BigDecimal(admissionEstimateReference.getScoreParagraph().split("-")[0]), 2, RoundingMode.HALF_UP);
                return insideResult.compareTo(new BigDecimal(15)) < 0;
            };
            major.setStarRating(ExUtils.starRatingHandler(result, supplier));

            String collectionStatus = "";
            major.setCollectionStatus(collectionStatus);
        }

        pager.getPager().setRecords(list);
        pager.getPager().setTotal(total);
        return new ExecuteResult(pager);
    }

    @GetMapping("/detail")
    public ExecuteResult detail(Long id) {
        return new ExecuteResult(majorService.getById(id));
    }

}
