package com.home.examination.controller.app;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.home.examination.entity.domain.HistoryAdmissionDataDO;
import com.home.examination.entity.domain.MajorDO;
import com.home.examination.entity.domain.SchoolDO;
import com.home.examination.entity.domain.SchoolMajorDO;
import com.home.examination.entity.vo.AdmissionEstimateReferenceDO;
import com.home.examination.entity.vo.ExecuteResult;
import com.home.examination.entity.vo.TargetCollegesVO;
import com.home.examination.service.HistoryAdmissionDataService;
import com.home.examination.service.MajorService;
import com.home.examination.service.SchoolMajorService;
import com.home.examination.service.SchoolService;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.util.List;
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

    @PostMapping("/detail")
    public ExecuteResult detail(String educationalCode, Long majorId) {
        TargetCollegesVO targetColleges = new TargetCollegesVO();

        SchoolDO school = schoolService.getByEducationalCode(educationalCode);
        if (majorId != null && educationalCode != null) {
            MajorDO one = majorService.getById(majorId);

            LambdaQueryWrapper<SchoolMajorDO> schoolMajorQueryWrapper = new LambdaQueryWrapper<>();
            schoolMajorQueryWrapper.eq(SchoolMajorDO::getMajorId, one.getId());
            List<SchoolMajorDO> schoolMajorList = schoolMajorService.list(schoolMajorQueryWrapper);
            List<String> collect = schoolMajorList.stream().map(SchoolMajorDO::getEducationalCode).collect(Collectors.toList());

            LambdaQueryWrapper<SchoolDO> schoolQueryWrapper = new LambdaQueryWrapper<>();
            schoolQueryWrapper.in(SchoolDO::getEducationalCode, collect);
            List<SchoolDO> list = schoolService.list(schoolQueryWrapper);

            targetColleges.setSchoolList(list);

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
                targetColleges.setMajorList(majorList);
            }
        }

        LambdaQueryWrapper<HistoryAdmissionDataDO> historyQueryWrapper = new LambdaQueryWrapper<>();
        historyQueryWrapper.eq(HistoryAdmissionDataDO::getEducationalCode, educationalCode);
        // 历史录取数据列表
        List<HistoryAdmissionDataDO> historyAdmissionDataList = historyAdmissionDataService.list(historyQueryWrapper);
        targetColleges.setHistoryAdmissionDataList(historyAdmissionDataList);

        int year = LocalDate.now().minusYears(3).getYear();
        historyQueryWrapper.eq(majorId != null, HistoryAdmissionDataDO::getMajorId, majorId)
                .apply("years >= {0}", year);
        AdmissionEstimateReferenceDO admissionEstimateReference = historyAdmissionDataService.getBySchoolOrMajor(historyQueryWrapper);
        targetColleges.setAdmissionEstimateReference(admissionEstimateReference);

        return new ExecuteResult(targetColleges);
    }

}
