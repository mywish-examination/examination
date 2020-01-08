package com.home.examination.controller.web;

import com.home.examination.entity.domain.FeedbackDO;
import com.home.examination.entity.page.FeedbackPager;
import com.home.examination.service.FeedbackService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/web/feedback")
public class FeedbackController {

    @Resource
    private FeedbackService feedbackService;

    @PostMapping("/listPage")
    @ResponseBody
    public FeedbackPager listPage(FeedbackPager pager) {
        feedbackService.page(pager.getPager());
        return pager;
    }

    @PostMapping("/delete")
    @ResponseBody
    public Map<String, String> delete(Long id) {
        boolean result = feedbackService.removeById(id);
        Map<String, String> map = new HashMap<>();
        map.put("status", result ? "success" : "error");
        return map;
    }

    @GetMapping("/detail")
    public ModelAndView detail(Long id, Model model) {
        FeedbackDO feedbackDO = feedbackService.getById(id);
        ModelAndView mav = new ModelAndView("/pages/contentInformation/feedback/modify");
        model.addAttribute("feedback", feedbackDO);
        return mav;
    }

    @PostMapping("/saveOrUpdate")
    public ModelAndView saveOrUpdate(FeedbackDO param) {
        ModelAndView mav = new ModelAndView("/pages/contentInformation/feedback/list");
        feedbackService.saveOrUpdate(param);
        return mav;
    }

}
