package com.home.examination.controller.app;

import com.alibaba.druid.util.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.home.examination.entity.domain.CityDO;
import com.home.examination.entity.vo.ExecuteResult;
import com.home.examination.service.CityService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/app/city")
public class CityAppController {

    @Resource
    private CityService cityService;

    @PostMapping("/list")
    @ResponseBody
    public ExecuteResult list(CityDO cityDO) {
        LambdaQueryWrapper<CityDO> queryWrapper = new LambdaQueryWrapper<>();
        if (cityDO != null) {
            queryWrapper.eq(cityDO.getPid() != null, CityDO::getPid, cityDO.getPid())
                    .eq(!StringUtils.isEmpty(cityDO.getType()), CityDO::getType, cityDO.getType());
        }
        return new ExecuteResult(cityService.list(queryWrapper));
    }

}
