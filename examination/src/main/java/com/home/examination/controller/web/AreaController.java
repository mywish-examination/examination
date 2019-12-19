package com.home.examination.controller.web;

import com.home.examination.entity.domain.AreaDO;
import com.home.examination.entity.domain.SchoolDO;
import com.home.examination.entity.page.Pager;
import com.home.examination.service.AreaService;
import com.home.examination.service.SchoolService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;

@RestController
@RequestMapping("/web/area")
public class AreaController {

    @Resource
    private AreaService areaService;

    @PostMapping("/listPage")
    public ModelAndView listPage(Pager<AreaDO> pager) {
        Pager<AreaDO> schoolPager = areaService.listPage(pager);
        ModelAndView modelAndView = new ModelAndView("/area/schoolList");
        modelAndView.addObject("schoolPager", schoolPager);

        return modelAndView;
    }

    @PostMapping("/delete")
    public ModelAndView delete(Pager<AreaDO> pager) {
        Pager<AreaDO> schoolPager = areaService.listPage(pager);
        ModelAndView modelAndView = new ModelAndView("/area/schoolList");
        modelAndView.addObject("schoolPager", schoolPager);

        return modelAndView;
    }

    @PostMapping("/saveOrUpdate")
    public ModelAndView saveOrUpdate(Pager<AreaDO> pager) {
        Pager<AreaDO> schoolPager = areaService.listPage(pager);
        ModelAndView modelAndView = new ModelAndView("/area/schoolList");
        modelAndView.addObject("schoolPager", schoolPager);

        return modelAndView;
    }

}
