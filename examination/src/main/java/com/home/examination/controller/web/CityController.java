package com.home.examination.controller.web;

import com.alibaba.druid.util.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.home.examination.entity.domain.CityDO;
import com.home.examination.entity.domain.UserDO;
import com.home.examination.entity.page.UserPager;
import com.home.examination.entity.vo.SuggestVO;
import com.home.examination.service.CityService;
import com.home.examination.service.UserService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

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