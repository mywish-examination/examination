package com.home.examination.controller.web;

import com.home.examination.entity.domain.FeedbackDO;
import com.home.examination.entity.domain.SchoolDO;
import com.home.examination.entity.page.Pager;
import com.home.examination.service.FeedbackService;
import com.home.examination.service.SchoolService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;

@Controller
@RequestMapping("/web/feedback")
public class FeedbackController {

    @Resource
    private FeedbackService feedbackService;

    @PostMapping("/listPage")
    public ModelAndView listPage(Pager<FeedbackDO> pager) {
        Pager<FeedbackDO> schoolPager = feedbackService.listPage(pager);
        ModelAndView modelAndView = new ModelAndView("/school/schoolList");
        modelAndView.addObject("schoolPager", schoolPager);

        return modelAndView;
    }

}
