package com.home.examination.controller.app;

import com.alibaba.druid.util.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.home.examination.common.config.ConstantHandler;
import com.home.examination.common.utils.BaseCacheUtils;
import com.home.examination.common.utils.ExUtils;
import com.home.examination.entity.domain.*;
import com.home.examination.entity.vo.AdmissionEstimateReferenceDO;
import com.home.examination.entity.vo.ExecuteResult;
import com.home.examination.entity.vo.TargetCollegesConfigVO;
import com.home.examination.entity.vo.TargetCollegesVO;
import com.home.examination.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collections;
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
    private SchoolPlanService schoolPlanService;
    @Resource
    private SubsectionService subsectionService;

    @Resource
    private BaseCacheUtils baseCacheUtils;

    @Resource
    private RedisTemplate redisTemplate;

    private final static BigDecimal zero = new BigDecimal(0);

    private final static Logger log = LoggerFactory.getLogger(TargetCollegesController.class.getName());

    /**
     * @param historyAdmissionDataDO
     * @param token
     * @param sortType               1：星级，2：名称
     * @return
     */
    @PostMapping("/detail")
    public ExecuteResult<TargetCollegesVO> detail(HistoryAdmissionDataDO historyAdmissionDataDO, String token, String sortType) {
        UserDO userDO = (UserDO) redisTemplate.opsForValue().get(token);
        TargetCollegesVO targetColleges = new TargetCollegesVO();
        String educationalCode = historyAdmissionDataDO.getEducationalCode();
        SchoolDO school = schoolService.getByEducationalCode(educationalCode);
        targetColleges.setSchoolName(school.getName());
        Long majorId = historyAdmissionDataDO.getMajorId();

        // 设置用户的学科类别
        historyAdmissionDataDO.setUserSubjectType(userDO.getSubjectType());

        // 历史录取数据列表
        List<HistoryAdmissionDataDO> historyAdmissionDataList = historyAdmissionDataService.listHistoryAdmissionDataGroupYears(historyAdmissionDataDO);
        targetColleges.setHistoryAdmissionDataList(historyAdmissionDataList);

        LambdaQueryWrapper<HistoryAdmissionDataDO> historyQueryWrapper = new LambdaQueryWrapper<>();
        historyQueryWrapper.eq(HistoryAdmissionDataDO::getEducationalCode, educationalCode)
                .eq(majorId != null, HistoryAdmissionDataDO::getMajorId, majorId)
                .eq(!StringUtils.isEmpty(historyAdmissionDataDO.getBatchCode()), HistoryAdmissionDataDO::getBatchCode, historyAdmissionDataDO.getBatchCode())
                .apply(!StringUtils.isEmpty(historyAdmissionDataDO.getRemark()), " remark like '%{0}%'", historyAdmissionDataDO.getRemark());

        AdmissionEstimateReferenceDO admissionEstimateReference = getAdmissionEstimate(historyAdmissionDataList, userDO.getSubjectType());

        BigDecimal schoolStarRating = historyAdmissionDataService.probabilityFilingHandler(historyAdmissionDataList, userDO.getRank());
        String probabilityFiling = schoolStarRating.multiply(new BigDecimal(100)).intValue() + "%";
        String scoreParagraph = admissionEstimateReference.getScoreParagraph();
        if (schoolStarRating.compareTo(zero) == 0) {
            admissionEstimateReference.setStarRating("0");
        } else {
            String starRating = ExUtils.starRatingHandler(schoolStarRating);
            if (starRating.equals("0")) {
                Supplier<Boolean> supplier = ExUtils.getUserHalfStarSupplier(userDO, scoreParagraph);
                if (supplier.get()) {
                    starRating = "0.5";
                    probabilityFiling = "1%";
                } else probabilityFiling = "0%";
            }
            admissionEstimateReference.setStarRating(starRating);
            targetColleges.setAdmissionEstimateReference(admissionEstimateReference);
        }
        admissionEstimateReference.setProbabilityFiling(probabilityFiling);

        // 专业和学校最下面的列表处理
        LambdaQueryWrapper<SchoolPlanDO> schoolPlanQueryWrapper = null;
        if (majorId != null) {
            MajorDO major = majorService.getById(majorId);
            List<SchoolDO> schoolList = schoolService.listSchool(majorId, userDO.getId(), historyAdmissionDataDO.getBatchCode(), historyAdmissionDataDO.getRemark());
            if (CollectionUtils.isEmpty(schoolList)) return new ExecuteResult(targetColleges);

            for (SchoolDO schoolDO : schoolList) {
                // 设置每个学校的招生人数
                schoolDO.setEnrolment(schoolPlanService.getPlanNum(school.getName(), major.getName()));

                historyQueryWrapper = new LambdaQueryWrapper<>();
                historyQueryWrapper.eq(HistoryAdmissionDataDO::getEducationalCode, schoolDO.getEducationalCode()).eq(HistoryAdmissionDataDO::getMajorId, majorId);
                // 历史录取数据列表
                historyAdmissionDataList = historyAdmissionDataService.list(historyQueryWrapper);

                BigDecimal result = historyAdmissionDataService.probabilityFilingHandler(historyAdmissionDataList, userDO.getRank());
                if (result.compareTo(zero) == 0) {
                    admissionEstimateReference.setStarRating("0");
                } else {
                    String starRating = ExUtils.starRatingHandler(result);
                    if (starRating.equals("0")) {
                        Supplier<Boolean> supplier = ExUtils.getUserHalfStarSupplier(userDO, scoreParagraph);
                        if (supplier.get()) starRating = "0.5";
                    }
                    schoolDO.setStarRating(starRating);
                }
            }

            targetColleges.setSchoolList(schoolList);
            targetColleges.setMajorName(major.getName());
        } else {
            BeanUtils.copyProperties(school, targetColleges);

            // 专业列表
            List<MajorDO> majorList = majorService.listMajor(educationalCode, userDO.getId(), historyAdmissionDataDO.getBatchCode(), historyAdmissionDataDO.getRemark());

            if (CollectionUtils.isEmpty(majorList)) return new ExecuteResult(targetColleges);

            for (MajorDO major : majorList) {
                major.setEnrolment(schoolPlanService.getPlanNum(school.getName(), major.getName()));

                historyQueryWrapper = new LambdaQueryWrapper<>();
                historyQueryWrapper.eq(HistoryAdmissionDataDO::getEducationalCode, educationalCode).eq(HistoryAdmissionDataDO::getMajorId, major.getId());
                // 历史录取数据列表
                historyAdmissionDataList = historyAdmissionDataService.list(historyQueryWrapper);

                BigDecimal result = historyAdmissionDataService.probabilityFilingHandler(historyAdmissionDataList, userDO.getRank());
                if (result.compareTo(zero) == 0) {
                    admissionEstimateReference.setStarRating("0");
                } else {
                    String starRating = ExUtils.starRatingHandler(result);
                    if (starRating.equals("0")) {
                        Supplier<Boolean> supplier = ExUtils.getUserHalfStarSupplier(userDO, scoreParagraph);
                        if (supplier.get()) starRating = "0.5";
                    }
                    major.setStarRating(starRating);
                }
            }

            targetColleges.setMajorList(majorList);
        }

        log.info("targetColleges ========================================={}, {}", targetColleges, targetColleges.getMajorList());

        // 处理排序、和计划招生数
        setOtherInfo(sortType, targetColleges, majorId);
        return new ExecuteResult(targetColleges);
    }

    private void setOtherInfo(String sortType, TargetCollegesVO targetColleges, Long majorId) {
        if (!CollectionUtils.isEmpty(targetColleges.getSchoolList()) && majorId != null) {
            if (StringUtils.isEmpty(sortType) || sortType.equals("1"))
                targetColleges.getSchoolList().sort(Comparator.comparing(SchoolDO::getStarRating));
            else
                targetColleges.getSchoolList().sort(Comparator.comparing(SchoolDO::getName));
        } else if (!CollectionUtils.isEmpty(targetColleges.getMajorList())) {
            if (StringUtils.isEmpty(sortType) || sortType.equals("1"))
                targetColleges.getMajorList().sort(Comparator.comparing(MajorDO::getStarRating));
            else
                targetColleges.getMajorList().sort(Comparator.comparing(MajorDO::getName));
        }
    }

    public AdmissionEstimateReferenceDO getAdmissionEstimate(List<HistoryAdmissionDataDO> historyAdmissionDataList, String subjectType) {
        AdmissionEstimateReferenceDO result = new AdmissionEstimateReferenceDO();

        // 最高位-位次
        List<Integer> highestRankList = historyAdmissionDataList.stream().map(HistoryAdmissionDataDO::getHighestRank).collect(Collectors.toList());
        Integer highestRankMax = highestRankList.stream().max(Comparator.comparing(Integer::intValue)).orElse(0);
        highestRankList.removeIf(num -> num.intValue() == highestRankMax.intValue());
        Integer highestRankSum = highestRankList.stream().reduce(0, (a, b) -> a + b);
        int highestRank = new BigDecimal(highestRankSum).divide(new BigDecimal(highestRankList.size())).setScale(0, RoundingMode.HALF_UP).intValue();

        // 最低位-位次
        List<Integer> minimumRankList = historyAdmissionDataList.stream().map(HistoryAdmissionDataDO::getMinimumRank).collect(Collectors.toList());
        Integer minimumRankMax = minimumRankList.stream().max(Comparator.comparing(Integer::intValue)).orElse(0);
        minimumRankList.removeIf(num -> num.intValue() == minimumRankMax.intValue());
        Integer minimumRankSum = minimumRankList.stream().reduce(0, (a, b) -> a + b);
        int minimumRank = new BigDecimal(minimumRankSum).divide(new BigDecimal(minimumRankList.size())).setScale(0, RoundingMode.HALF_UP).intValue();

        int highestScore = subsectionService.getScore(subjectType, highestRank);
        int minScore = subsectionService.getScore(subjectType, minimumRank);

        result.setRankParagraph(minimumRank + "-" + highestRank);
        result.setScoreParagraph(highestScore + "-" + minScore);

        return result;
    }

    @PostMapping("/listConfig")
    public ExecuteResult<TargetCollegesConfigVO> listConfig(String educationalCode, Long majorId, String token) {
        UserDO userDO = (UserDO) redisTemplate.opsForValue().get(token);
        ExecuteResult<TargetCollegesConfigVO> er = new ExecuteResult<>();
        TargetCollegesConfigVO result = new TargetCollegesConfigVO();

        // 历史录取数据列表
        List<HistoryAdmissionDataDO> historyAdmissionDataList = historyAdmissionDataService.listBaseDictData(educationalCode, majorId, userDO.getSubjectType());
        List<DataDictionaryDO> remarkCacheDictList = baseCacheUtils.listDictByCode("dict_history_remark");
        List<DataDictionaryDO> batchCodeCacheDictList = baseCacheUtils.listDictByCode("dict_batch_code");

        log.info("redis 缓存 按需内容 集合 list: {}", remarkCacheDictList);
        log.info("redis 缓存 批次 集合 list: {}", batchCodeCacheDictList);

        List<String> batchCodeCollect = historyAdmissionDataList.stream().map(HistoryAdmissionDataDO::getBatchCode).distinct().collect(Collectors.toList());
        batchCodeCollect.removeAll(Collections.singleton(null));
        if (!CollectionUtils.isEmpty(batchCodeCollect) && !CollectionUtils.isEmpty(batchCodeCacheDictList)) {
            List<DataDictionaryDO> batchCodeList = batchCodeCacheDictList.stream().filter(dict -> batchCodeCollect.contains(dict.getDictNum())).collect(Collectors.toList());
            result.setBatchDictList(batchCodeList);

            log.info("数据字典 批次集合 list:{}", batchCodeList);
        }

        List<String> remarkCollect = historyAdmissionDataList.stream().map(HistoryAdmissionDataDO::getRemark).distinct().collect(Collectors.toList());
        remarkCollect.removeAll(Collections.singleton(null));
        if (!CollectionUtils.isEmpty(remarkCollect) && !CollectionUtils.isEmpty(remarkCacheDictList)) {
            List<DataDictionaryDO> remarkList = remarkCacheDictList.stream().filter(dict -> remarkCollect.stream().anyMatch(remark -> remark.indexOf(dict.getDictNum()) > -1)).collect(Collectors.toList());
            result.setRemarkDictList(remarkList);

            log.info("数据字典 按需内容集合 list:{}", remarkList);
        }

        er.setData(result);
        return er;
    }

}
