package com.home.examination.controller.web;

import com.alibaba.druid.util.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.home.examination.entity.domain.CityDO;
import com.home.examination.entity.vo.SuggestVO;
import com.home.examination.service.CityService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

@Controller
@RequestMapping("/web/city")
public class CityController {

    @Resource
    private CityService cityService;

    @GetMapping("/listSuggest")
    @ResponseBody
    public SuggestVO<CityDO> listSuggest(CityDO cityDO) {
        LambdaQueryWrapper<CityDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.apply(!StringUtils.isEmpty(cityDO.getCityName()), " name like '%" + cityDO.getCityName() + "%'");
        List<CityDO> list = cityService.list(queryWrapper);

        return new SuggestVO<>(list);
    }

}