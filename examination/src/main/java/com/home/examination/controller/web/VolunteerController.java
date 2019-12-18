package com.home.examination.controller.web;

import com.home.examination.entity.domain.SchoolDO;
import com.home.examination.entity.domain.VolunteerDO;
import com.home.examination.entity.page.Pager;
import com.home.examination.service.SchoolService;
import com.home.examination.service.VolunteerService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;

@Controller
@RequestMapping("/web/volunteer")
public class VolunteerController {

    @Resource
    private VolunteerService volunteerService;

    @PostMapping("/listPage")
    public ModelAndView listPage(Pager<VolunteerDO> pager) {
        Pager<VolunteerDO> schoolPager = volunteerService.listPage(pager);
        ModelAndView modelAndView = new ModelAndView("/school/schoolList");
        modelAndView.addObject("schoolPager", schoolPager);

        return modelAndView;
    }

}
