package com.home.examination.controller.app;

import com.home.examination.entity.page.NewsInformationPager;
import com.home.examination.entity.vo.ExecuteResult;
import com.home.examination.service.NewsInformationService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/app/newsInformation")
public class NewsInformationAppController {

    @Resource
    private NewsInformationService newsInformationService;

    @PostMapping("/listPage")
    public ExecuteResult listPage(NewsInformationPager pager) {
        return new ExecuteResult(newsInformationService.page(pager.getPager()));
    }

    @GetMapping("/detail")
    public ExecuteResult detail(Long id) {
        return new ExecuteResult(newsInformationService.getById(id));
    }

}
