package com.home.examination.controller.app;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.home.examination.entity.domain.HistoryAdmissionDataDO;
import com.home.examination.entity.domain.MajorDO;
import com.home.examination.entity.domain.SchoolDO;
import com.home.examination.entity.domain.SchoolMajorDO;
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
    public ExecuteResult detail(Long schoolId, Long majorId) {
        TargetCollegesVO targetColleges = new TargetCollegesVO();
        SchoolDO school = schoolService.getById(schoolId);
        if (majorId == null && schoolId != null) {
            BeanUtils.copyProperties(school, targetColleges);
            targetColleges.setSchoolName(school.getName());

            LambdaQueryWrapper<HistoryAdmissionDataDO> historyQueryWrapper = new LambdaQueryWrapper<>();
            historyQueryWrapper.eq(HistoryAdmissionDataDO::getSchoolId, schoolId);
            // 历史录取数据列表
            List<HistoryAdmissionDataDO> historyAdmissionDataList = historyAdmissionDataService.list(historyQueryWrapper);


            LambdaQueryWrapper<SchoolMajorDO> schoolMajorQueryWrapper = new LambdaQueryWrapper<>();
            schoolMajorQueryWrapper.eq(SchoolMajorDO::getSchoolId, schoolId);
            List<SchoolMajorDO> schoolMajorList = schoolMajorService.list(schoolMajorQueryWrapper);
            List<Long> collect = schoolMajorList.stream().map(SchoolMajorDO::getMajorId).collect(Collectors.toList());

            LambdaQueryWrapper<MajorDO> majorQueryWrapper = new LambdaQueryWrapper<>();
            majorQueryWrapper.in(MajorDO::getId, collect);
            // 专业列表
            List<MajorDO> majorList = majorService.list(majorQueryWrapper);

            targetColleges.setHistoryAdmissionDataList(historyAdmissionDataList);
            targetColleges.setMajorList(majorList);
        }

        if (majorId != null && schoolId != null) {
            MajorDO one = majorService.getById(majorId);

            LambdaQueryWrapper<SchoolMajorDO> schoolMajorQueryWrapper = new LambdaQueryWrapper<>();
            schoolMajorQueryWrapper.eq(SchoolMajorDO::getMajorId, one.getId());
            List<SchoolMajorDO> schoolMajorList = schoolMajorService.list(schoolMajorQueryWrapper);
            List<Long> collect = schoolMajorList.stream().map(SchoolMajorDO::getSchoolId).collect(Collectors.toList());

            LambdaQueryWrapper<SchoolDO> schoolQueryWrapper = new LambdaQueryWrapper<>();
            schoolQueryWrapper.in(SchoolDO::getId, collect);
            List<SchoolDO> list = schoolService.list(schoolQueryWrapper);

            targetColleges.setSchoolList(list);
            

            targetColleges.setSchoolName(school.getName());
            targetColleges.setMajorName(one.getName());

            LambdaQueryWrapper<HistoryAdmissionDataDO> historyQueryWrapper = new LambdaQueryWrapper<>();
            historyQueryWrapper.eq(HistoryAdmissionDataDO::getSchoolId, schoolId);
            // 历史录取数据列表
            List<HistoryAdmissionDataDO> historyAdmissionDataList = historyAdmissionDataService.list(historyQueryWrapper);

            targetColleges.setHistoryAdmissionDataList(historyAdmissionDataList);
        }

        return new ExecuteResult(targetColleges);
    }

}
