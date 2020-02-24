package com.home.examination.controller.app;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.home.examination.common.utils.ExUtils;
import com.home.examination.entity.domain.HistoryAdmissionDataDO;
import com.home.examination.entity.domain.MajorDO;
import com.home.examination.entity.domain.MyCollectionDO;
import com.home.examination.entity.domain.UserDO;
import com.home.examination.entity.page.MajorPager;
import com.home.examination.entity.vo.AdmissionEstimateReferenceDO;
import com.home.examination.entity.vo.ExecuteResult;
import com.home.examination.service.HistoryAdmissionDataService;
import com.home.examination.service.MajorService;
import com.home.examination.service.MyCollectionService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.math.BigDecimal;
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

    @RequestMapping("/listPage")
    @ResponseBody
    public ExecuteResult listPage(MajorPager pager) {
        LambdaQueryWrapper<MajorDO> queryWrapper = new LambdaQueryWrapper<>();
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
        for (MajorDO major:
             list) {
            if(existMyCollectionCollect.contains(major.getId())) major.setCollectionStatus("1");

            LambdaQueryWrapper<HistoryAdmissionDataDO> historyQueryWrapper = new LambdaQueryWrapper<>();
            historyQueryWrapper.eq(major.getId() != null, HistoryAdmissionDataDO::getMajorId, major.getId());
            // 历史录取数据列表
            List<HistoryAdmissionDataDO> historyAdmissionDataList = historyAdmissionDataService.list(historyQueryWrapper);

            historyQueryWrapper.apply("years >= {0}", year);
            AdmissionEstimateReferenceDO admissionEstimateReference = historyAdmissionDataService.getBySchoolOrMajor(historyQueryWrapper);

            BigDecimal result = historyAdmissionDataService.probabilityFilingHandler(historyAdmissionDataList, user);
            Supplier<Boolean> supplier = () -> new BigDecimal(user.getCollegeScore()).divide(new BigDecimal(admissionEstimateReference.getScoreParagraph().split("-")[0])).compareTo(new BigDecimal(15)) < 0;
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
