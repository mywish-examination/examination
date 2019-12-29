package com.home.examination.controller.app;

import com.home.examination.entity.domain.MyCollectionDO;
import com.home.examination.entity.page.Pager;
import com.home.examination.service.MyCollectionService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/app/myCollection")
public class MyCollectionAppController {

    @Resource
    private MyCollectionService myCollectionService;

    @PostMapping("/listPage")
    public Pager<MyCollectionDO> listPage(Pager<MyCollectionDO> pager) {
        myCollectionService.page(pager.getPager());
        return pager;
    }

    @PostMapping("/delete")
    public Map<String, String> delete(Long id) {
        boolean result = myCollectionService.removeById(id);
        Map<String, String> map = new HashMap<>();
        map.put("status", result ? "success" : "error");
        return map;
    }

    @GetMapping("/detail")
    public MyCollectionDO detail(Long id) {
        return myCollectionService.getById(id);
    }

    @PostMapping("/saveOrUpdate")
    public Map<String, String> saveOrUpdate(MyCollectionDO param) {
        boolean result = myCollectionService.saveOrUpdate(param);
        Map<String, String> map = new HashMap<>();
        map.put("status", result ? "success" : "error");
        return map;
    }

}
