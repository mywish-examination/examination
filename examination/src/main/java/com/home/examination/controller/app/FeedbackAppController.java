package com.home.examination.controller.app;

import com.home.examination.entity.domain.FeedbackDO;
import com.home.examination.entity.page.FeedbackPager;
import com.home.examination.entity.vo.ExecuteResult;
import com.home.examination.service.FeedbackService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/app/feedback")
public class FeedbackAppController {

    @Resource
    private FeedbackService feedbackService;

    @PostMapping("/listPage")
    public ExecuteResult listPage(FeedbackPager pager) {
        return new ExecuteResult(feedbackService.page(pager.getPager()));
    }

    @PostMapping("/delete")
    public Map<String, String> delete(Long id) {
        boolean result = feedbackService.removeById(id);
        Map<String, String> map = new HashMap<>();
        map.put("status", result ? "success" : "error");
        return map;
    }

    @GetMapping("/detail")
    public FeedbackDO detail(Long id) {
        return feedbackService.getById(id);
    }

    @PostMapping("/saveOrUpdate")
    public ExecuteResult saveOrUpdate(FeedbackDO param) {
        return new ExecuteResult(feedbackService.saveOrUpdate(param));
    }

}
