package com.home.examination.controller.app;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.home.examination.entity.domain.FeatureDO;
import com.home.examination.entity.vo.ExecuteResult;
import com.home.examination.entity.vo.FeatureVO;
import com.home.examination.service.FeatureService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/app/range")
public class RangeAppController {

    @Resource
    private FeatureService featureService;

    @PostMapping("/list")
    @ResponseBody
    public ExecuteResult list(String type) {
        LambdaQueryWrapper<FeatureDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(FeatureDO::getFeatureType, type);
        List<FeatureDO> list = featureService.list(queryWrapper);
        Map<String, List<FeatureDO>> collect = list.stream().collect(Collectors.groupingBy(featureDO -> featureDO.getFeatureCode() + "-" + featureDO.getFeatureName()));

        List<FeatureVO> resultList = new ArrayList<>();
        for (Map.Entry<String, List<FeatureDO>> entry : collect.entrySet()) {
            FeatureVO featureVO = new FeatureVO();
            featureVO.setFeatureCode(entry.getKey().split("-")[0]);
            featureVO.setFeatureName(entry.getKey().split("-")[1]);
            featureVO.setList(entry.getValue());
            resultList.add(featureVO);
        }
        return new ExecuteResult(resultList);
    }

}
