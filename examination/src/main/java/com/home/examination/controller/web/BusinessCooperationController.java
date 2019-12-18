package com.home.examination.controller.web;

import com.home.examination.entity.domain.BusinessCooperationDO;
import com.home.examination.entity.domain.SchoolDO;
import com.home.examination.entity.page.Pager;
import com.home.examination.service.BusinessCooperationService;
import com.home.examination.service.SchoolService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;

@Controller
@RequestMapping("/web/businessCooperation")
public class BusinessCooperationController {

    @Resource
    private BusinessCooperationService businessCooperationService;

    @PostMapping("/listPage")
    public ModelAndView listPage(Pager<BusinessCooperationDO> pager) {
        Pager<BusinessCooperationDO> schoolPager = businessCooperationService.listPage(pager);
        ModelAndView modelAndView = new ModelAndView("/school/schoolList");
        modelAndView.addObject("schoolPager", schoolPager);

        return modelAndView;
    }

}
