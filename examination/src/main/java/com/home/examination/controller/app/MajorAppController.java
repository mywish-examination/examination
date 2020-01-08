package com.home.examination.controller.app;

import com.home.examination.entity.vo.ExecuteResult;
import com.home.examination.entity.page.MajorPager;
import com.home.examination.service.MajorService;
import com.home.examination.service.SchoolMajorService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/app/major")
public class MajorAppController {

    @Resource
    private MajorService majorService;
    @Resource
    private SchoolMajorService schoolService;

    @RequestMapping("/listPage")
    @ResponseBody
    public ExecuteResult listPage(MajorPager pager) {
        return new ExecuteResult(majorService.page(pager.getPager()));
    }

    @GetMapping("/detail")
    public ExecuteResult detail(Long id) {
        return new ExecuteResult(majorService.getById(id));
    }

}
