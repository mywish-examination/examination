package com.home.examination.controller.app;

import com.home.examination.entity.domain.NewsInformationDO;
import com.home.examination.entity.page.Pager;
import com.home.examination.service.NewsInformationService;
import org.springframework.beans.factory.annotation.Value;
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
    @Value("${examination.upload.newsInformation-url}")
    private String schoolUrl;

    @PostMapping("/listPage")
    public Pager<NewsInformationDO> listPage(Pager<NewsInformationDO> pager) {
        newsInformationService.page(pager.getPager());
        return pager;
    }

    @GetMapping("/detail")
    public NewsInformationDO detail(Long id) {
        return newsInformationService.getById(id);
    }

}
