package com.home.examination.controller.app;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.home.examination.entity.domain.*;
import com.home.examination.entity.vo.ExecuteResult;
import com.home.examination.entity.vo.TargetCollegesVO;
import com.home.examination.service.HistoryAdmissionDataService;
import com.home.examination.service.MajorService;
import com.home.examination.service.SchoolMajorService;
import com.home.examination.service.SchoolService;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/app/targetColleges")
public class TargetCollegesController {

    @Resource
    private MajorService majorService;
    @Resource
    private SchoolService schoolService;
    @Resource
    private HistoryAdmissionDataService historyAdmissionDataService;

    @PostMapping("/detail")
    public ExecuteResult detail(Long schoolId, Long majorId) {
        TargetCollegesVO targetColleges = new TargetCollegesVO();
        if(schoolId != null) {
            SchoolDO schoolDO = schoolService.getById(schoolId);
            BeanUtils.copyProperties(schoolDO, targetColleges);
            targetColleges.setSchoolName(schoolDO.getName());

            LambdaQueryWrapper<HistoryAdmissionDataDO> historyQueryWrapper = new LambdaQueryWrapper<>();
            historyQueryWrapper.eq(HistoryAdmissionDataDO::getSchoolId, schoolId);
            // 历史录取数据列表
            List<HistoryAdmissionDataDO> historyAdmissionDataList = historyAdmissionDataService.list(historyQueryWrapper);

            LambdaQueryWrapper<MajorDO> majorQueryWrapper = new LambdaQueryWrapper<>();
            majorQueryWrapper.eq(MajorDO::getSchoolId, schoolId);
            // 专业列表
            List<MajorDO> majorList = majorService.list(majorQueryWrapper);

            targetColleges.setHistoryAdmissionDataList(historyAdmissionDataList);
            targetColleges.setMajorList(majorList);
        }

        if(majorId != null) {
            MajorDO one = majorService.getById(majorId);

            Long bySchoolId = one.getSchoolId();
            SchoolDO schoolDO = schoolService.getById(bySchoolId);
            BeanUtils.copyProperties(schoolDO, targetColleges);

            targetColleges.setSchoolName(schoolDO.getName());
            targetColleges.setMajorName(one.getSubjectName());

            LambdaQueryWrapper<HistoryAdmissionDataDO> historyQueryWrapper = new LambdaQueryWrapper<>();
            historyQueryWrapper.eq(HistoryAdmissionDataDO::getSchoolId, bySchoolId);
            // 历史录取数据列表
            List<HistoryAdmissionDataDO> historyAdmissionDataList = historyAdmissionDataService.list(historyQueryWrapper);

            LambdaQueryWrapper<MajorDO> majorQueryWrapper = new LambdaQueryWrapper<>();
            majorQueryWrapper.eq(MajorDO::getSchoolId, bySchoolId);
            // 专业列表
            List<MajorDO> majorList = majorService.list(majorQueryWrapper);

            targetColleges.setHistoryAdmissionDataList(historyAdmissionDataList);
            targetColleges.setMajorList(majorList);
        }

        return new ExecuteResult(targetColleges);
    }

}
