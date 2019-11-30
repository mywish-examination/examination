package com.home.examination.controller.app;

import com.home.examination.entity.domain.SchoolDO;
import com.home.examination.entity.page.Pager;
import com.home.examination.service.SchoolService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/app/school")
public class SchoolAppController {

    @Resource
    private SchoolService schoolService;

    @PostMapping("/listPage")
    @ResponseBody
    public Pager<SchoolDO> listPage(@RequestBody Pager<SchoolDO> pager) {
        Pager<SchoolDO> schoolPager = schoolService.listPage(pager);

        return schoolPager;
    }
}
