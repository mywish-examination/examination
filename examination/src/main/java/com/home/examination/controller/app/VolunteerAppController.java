package com.home.examination.controller.app;

import com.home.examination.entity.domain.VolunteerDO;
import com.home.examination.entity.page.Pager;
import com.home.examination.service.VolunteerService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/app/volunteer")
public class VolunteerAppController {

    @Resource
    private VolunteerService volunteerService;

    @PostMapping("/listPage")
    public Pager<VolunteerDO> listPage(Pager<VolunteerDO> pager) {
        volunteerService.page(pager.getPager());
        return pager;
    }

    @PostMapping("/delete")
    public Map<String, String> delete(Long id) {
        boolean result = volunteerService.removeById(id);
        Map<String, String> map = new HashMap<>();
        map.put("status", result ? "success" : "error");
        return map;
    }

    @GetMapping("/detail")
    public VolunteerDO detail(Long id) {
        return volunteerService.getById(id);
    }

    @PostMapping("/saveOrUpdate")
    public Map saveOrUpdate(VolunteerDO param) {
        boolean result = volunteerService.saveOrUpdate(param);
        Map<String, String> map = new HashMap<>();
        map.put("status", result ? "success" : "error");
        return map;
    }

}
