package com.home.examination.controller.web;

import com.home.examination.entity.domain.NewsInformationDO;
import com.home.examination.entity.domain.SchoolDO;
import com.home.examination.entity.page.Pager;
import com.home.examination.service.NewsInformationService;
import com.home.examination.service.SchoolService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;

@Controller
@RequestMapping("/web/newInformation")
public class NewsInformationController {

    @Resource
    private NewsInformationService newsInformationService;

    @PostMapping("/listPage")
    public ModelAndView listPage(Pager<NewsInformationDO> pager) {
        Pager<NewsInformationDO> schoolPager = newsInformationService.listPage(pager);
        ModelAndView modelAndView = new ModelAndView("/school/schoolList");
        modelAndView.addObject("schoolPager", schoolPager);

        return modelAndView;
    }

}