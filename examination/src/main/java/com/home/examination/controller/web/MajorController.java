package com.home.examination.controller.web;

import com.home.examination.entity.domain.MajorDO;
import com.home.examination.entity.domain.SchoolDO;
import com.home.examination.entity.page.Pager;
import com.home.examination.service.MajorService;
import com.home.examination.service.SchoolService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;

@Controller
@RequestMapping("/web/major")
public class MajorController {

    @Resource
    private MajorService majorService;

    @PostMapping("/listPage")
    public ModelAndView listPage(Pager<MajorDO> pager) {
        Pager<MajorDO> schoolPager = majorService.listPage(pager);
        ModelAndView modelAndView = new ModelAndView("/school/schoolList");
        modelAndView.addObject("schoolPager", schoolPager);

        return modelAndView;
    }

}
