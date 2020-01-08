package com.home.examination.controller.app;

import com.home.examination.entity.vo.ExecuteResult;
import com.home.examination.entity.domain.VolunteerDO;
import com.home.examination.entity.page.VolunteerPager;
import com.home.examination.service.VolunteerService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/app/volunteer")
public class VolunteerAppController {

    @Resource
    private VolunteerService volunteerService;

    @PostMapping("/listPage")
    public ExecuteResult listPage(VolunteerPager pager) {
        return new ExecuteResult(volunteerService.page(pager.getPager()));
    }

    @PostMapping("/delete")
    public ExecuteResult delete(Long id) {
        volunteerService.removeById(id);
        return new ExecuteResult();
    }

    @GetMapping("/detail")
    public ExecuteResult detail(Long id) {
        return new ExecuteResult(volunteerService.getById(id));
    }

    @PostMapping("/saveOrUpdate")
    public ExecuteResult saveOrUpdate(VolunteerDO param) {
        return new ExecuteResult(volunteerService.saveOrUpdate(param));
    }

}
