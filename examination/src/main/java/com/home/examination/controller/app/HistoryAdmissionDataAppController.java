package com.home.examination.controller.app;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.home.examination.entity.domain.HistoryAdmissionDataDO;
import com.home.examination.entity.vo.ExecuteResult;
import com.home.examination.entity.vo.StudentSourceRankingVO;
import com.home.examination.service.HistoryAdmissionDataService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/app/historyAdmissionData")
public class HistoryAdmissionDataAppController {

    @Resource
    private HistoryAdmissionDataService historyAdmissionDataService;

    /**
     * 获取生源层级排名
     *
     * @return
     */
    @PostMapping("/list")
    @ResponseBody
    public ExecuteResult list(HistoryAdmissionDataDO requestParam) {
        LambdaQueryWrapper<HistoryAdmissionDataDO> queryWrapper = new LambdaQueryWrapper<>();
        int year = LocalDate.now().minusYears(3).getYear();
        String educationalCode = requestParam.getEducationalCode();
        queryWrapper.apply(" years >= {0}", year)
                .eq(HistoryAdmissionDataDO::getEducationalCode, educationalCode);

        List<HistoryAdmissionDataDO> historyAdmissionDataList = historyAdmissionDataService.list(queryWrapper);
        Map<String, List<HistoryAdmissionDataDO>> collect = historyAdmissionDataList.stream().collect(Collectors.groupingBy(HistoryAdmissionDataDO::getMajorName));
        List<StudentSourceRankingVO> list = new ArrayList<>();
        StudentSourceRankingVO studentSourceRankingVO;
        for (Map.Entry<String, List<HistoryAdmissionDataDO>> entry : collect.entrySet()) {
            studentSourceRankingVO = new StudentSourceRankingVO();
            int size = entry.getValue().size();
            Integer sumRank = entry.getValue().stream()
                    .map(historyAdmissionDataDO -> Integer.parseInt(historyAdmissionDataDO.getAvgRank())).reduce(0, (a, b) -> a + b);
            studentSourceRankingVO.setMajorName(entry.getKey());
            studentSourceRankingVO.setRank(sumRank);
            list.add(studentSourceRankingVO);
        }

        return new ExecuteResult();
    }

}
