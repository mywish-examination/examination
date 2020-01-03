package com.home.examination.controller.app;

import com.home.examination.entity.domain.ExecuteResult;
import com.home.examination.entity.domain.MajorDO;
import com.home.examination.entity.page.Pager;
import com.home.examination.service.MajorService;
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

    @RequestMapping("/listPage")
    @ResponseBody
    public ExecuteResult listPage(Pager<MajorDO> pager) {
        return new ExecuteResult(majorService.page(pager.getPager()));
    }

    @GetMapping("/detail")
    public ExecuteResult detail(Long id) {
        return new ExecuteResult(majorService.getById(id));
    }

}
