package com.home.examination.controller.web;

import com.home.examination.entity.domain.MyCollectionDO;
import com.home.examination.entity.page.MyCollectionPager;
import com.home.examination.service.MyCollectionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/web/myCollection")
public class MyCollectionController {

    @Resource
    private MyCollectionService myCollectionService;

    @PostMapping("/listPage")
    @ResponseBody
    public MyCollectionPager listPage(MyCollectionPager pager) {
        myCollectionService.page(pager.getPager());
        return pager;
    }

    @PostMapping("/delete")
    @ResponseBody
    public Map<String, String> delete(Long id) {
        boolean result = myCollectionService.removeById(id);
        Map<String, String> map = new HashMap<>();
        map.put("status", result ? "success" : "error");
        return map;
    }

    @GetMapping("/detail")
    public ModelAndView detail(Long id, Model model) {
        MyCollectionDO myCollectionDO = myCollectionService.getById(id);
        ModelAndView mav = new ModelAndView("/pages/contentInformation/myCollection/modify");
        model.addAttribute("myCollection", myCollectionDO);
        return mav;
    }

    @PostMapping("/saveOrUpdate")
    public ModelAndView saveOrUpdate(MyCollectionDO param) {
        ModelAndView mav = new ModelAndView("/pages/contentInformation/myCollection/list");
        myCollectionService.saveOrUpdate(param);
        return mav;
    }

}
