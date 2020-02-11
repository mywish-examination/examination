package com.home.examination.controller.app;

import com.home.examination.entity.vo.ExecuteResult;
import com.home.examination.entity.vo.MajorFeaturesVO;
import com.home.examination.entity.vo.SchoolFeaturesVO;
import com.home.examination.service.CityService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/app/range")
public class RangeAppController {

    @Resource
    private CityService cityService;
    @Resource
    private RedisTemplate redisTemplate;

    @PostMapping("/list")
    @ResponseBody
    public ExecuteResult list(String type) {
        // 专业
        if (type.equals("0")) {
            MajorFeaturesVO majorFeaturesVO = new MajorFeaturesVO();

            return new ExecuteResult(majorFeaturesVO);
        } else {
            // 院校
            SchoolFeaturesVO schoolFeatures = new SchoolFeaturesVO();

            return new ExecuteResult(schoolFeatures);
        }
    }

}
