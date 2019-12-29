package com.home.examination.controller.app;

import com.home.examination.entity.domain.SchoolDO;
import com.home.examination.entity.page.Pager;
import com.home.examination.service.SchoolService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/app/school")
public class SchoolAppController {

    @Resource
    private SchoolService schoolService;
    @Value("${examination.upload.school-url}")
    private String schoolUrl;

    @PostMapping("/listPage")
    public Pager<SchoolDO> listPage(Pager<SchoolDO> pager) {
        schoolService.page(pager.getPager());
        return pager;
    }

    @GetMapping("/detail")
    public SchoolDO detail(Long id) {
        return schoolService.getById(id);
    }

}
