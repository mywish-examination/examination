package com.home.examination.controller.app;

import com.home.examination.entity.domain.BusinessCooperationDO;
import com.home.examination.entity.page.BusinessCooperationPager;
import com.home.examination.entity.vo.ExecuteResult;
import com.home.examination.service.BusinessCooperationService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/app/businessCooperation")
@Deprecated
public class BusinessCooperationAppController {

    @Resource
    private BusinessCooperationService businessCooperationService;

    @PostMapping("/listPage")
    public ExecuteResult listPage(BusinessCooperationPager pager) {
        return new ExecuteResult(businessCooperationService.page(pager.getPager()));
    }

    @PostMapping("/delete")
    public Map<String, String> delete(Long id) {
        boolean result = businessCooperationService.removeById(id);
        Map<String, String> map = new HashMap<>();
        map.put("status", result ? "success" : "error");
        return map;
    }

    @GetMapping("/detail")
    public BusinessCooperationDO detail(Long id) {
        return businessCooperationService.getById(id);
    }

    @PostMapping("/saveOrUpdate")
    public Map<String, String> saveOrUpdate(BusinessCooperationDO param) {
        boolean result = businessCooperationService.saveOrUpdate(param);
        Map<String, String> map = new HashMap<>();
        map.put("status", result ? "success" : "error");
        return map;
    }

}
