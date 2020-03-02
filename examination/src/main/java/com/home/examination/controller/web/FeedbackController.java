package com.home.examination.controller.web;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.home.examination.entity.domain.FeedbackDO;
import com.home.examination.entity.domain.UserDO;
import com.home.examination.entity.page.FeedbackPager;
import com.home.examination.service.FeedbackService;
import com.home.examination.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.*;

@Controller
@RequestMapping("/web/feedback")
public class FeedbackController {

    @Resource
    private FeedbackService feedbackService;
    @Resource
    private UserService userService;

    @PostMapping("/listPage")
    @ResponseBody
    public FeedbackPager listPage(FeedbackPager pager) {
        LambdaQueryWrapper<FeedbackDO> queryWrapper = new LambdaQueryWrapper<>();
        int total = feedbackService.countByQueryWrapper(queryWrapper);
        List<FeedbackDO> list = Collections.emptyList();
        if (total > 0) {
            queryWrapper.last(String.format("limit %s, %s", pager.getPager().offset(), pager.getPager().getSize()));
            list = feedbackService.pageByQueryWrapper(queryWrapper);
        }

        pager.getPager().setTotal(total);
        pager.getPager().setRecords(list);
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

    @PostMapping("/deleteBatch")
    @ResponseBody
    public Map<String, String> deleteBatch(Long[] ids) {
        boolean result = feedbackService.removeByIds(Arrays.asList(ids));
        Map<String, String> map = new HashMap<>();
        map.put("status", result ? "success" : "error");
        return map;
    }

    @PostMapping("/deleteAll")
    @ResponseBody
    public Map<String, String> deleteBatch() {
        boolean result = feedbackService.remove(new LambdaQueryWrapper<>());
        Map<String, String> map = new HashMap<>();
        map.put("status", result ? "success" : "error");
        return map;
    }

    @GetMapping("/detail")
    public ModelAndView detail(Long id, Model model) {
        FeedbackDO feedbackDO = new FeedbackDO();
        if (id != null) {
            feedbackDO = feedbackService.getById(id);
            UserDO userDO = userService.getById(feedbackDO.getUserId());
            feedbackDO.setUserName(userDO.getTrueName());
        }
        ModelAndView mav = new ModelAndView("/pages/feedback/modify");
        model.addAttribute("feedback", feedbackDO);
        return mav;
    }

    @PostMapping("/saveOrUpdate")
    public ModelAndView saveOrUpdate(FeedbackDO param) {
        ModelAndView mav = new ModelAndView("/pages/feedback/list");
        feedbackService.saveOrUpdate(param);
        return mav;
    }

}
