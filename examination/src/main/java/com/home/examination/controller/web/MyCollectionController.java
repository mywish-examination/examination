package com.home.examination.controller.web;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.home.examination.entity.domain.MajorDO;
import com.home.examination.entity.domain.MyCollectionDO;
import com.home.examination.entity.domain.UserDO;
import com.home.examination.entity.page.MyCollectionPager;
import com.home.examination.service.MajorService;
import com.home.examination.service.MyCollectionService;
import com.home.examination.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.*;

@Controller
@RequestMapping("/web/myCollection")
public class MyCollectionController {

    @Resource
    private MyCollectionService myCollectionService;
    @Resource
    private UserService userService;
    @Resource
    private MajorService majorService;

    @PostMapping("/listPage")
    @ResponseBody
    public MyCollectionPager listPage(MyCollectionPager pager) {
        LambdaQueryWrapper<MyCollectionDO> queryWrapper = new LambdaQueryWrapper<>();
        int total = myCollectionService.countByQueryWrapper(queryWrapper);
        List<MyCollectionDO> list = Collections.emptyList();
        if (total > 0) {
            queryWrapper.last(String.format("limit %s, %s", pager.getPager().offset(), pager.getPager().getSize()));
            list = myCollectionService.pageByQueryWrapper(queryWrapper);
        }

        pager.getPager().setTotal(total);
        pager.getPager().setRecords(list);
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

    @PostMapping("/deleteBatch")
    @ResponseBody
    public Map<String, String> deleteBatch(Long[] ids) {
        boolean result = myCollectionService.removeByIds(Arrays.asList(ids));
        Map<String, String> map = new HashMap<>();
        map.put("status", result ? "success" : "error");
        return map;
    }

    @PostMapping("/deleteAll")
    @ResponseBody
    public Map<String, String> deleteBatch() {
        boolean result = myCollectionService.remove(new LambdaQueryWrapper<>());
        Map<String, String> map = new HashMap<>();
        map.put("status", result ? "success" : "error");
        return map;
    }

    @GetMapping("/detail")
    public ModelAndView detail(Long id, Model model) {
        MyCollectionDO myCollectionDO = new MyCollectionDO();
        if (id != null) {
            myCollectionDO = myCollectionService.getById(id);

            UserDO userDO = userService.getById(myCollectionDO.getUserId());
            myCollectionDO.setUserName(userDO.getTrueName());

            MajorDO majorDO = majorService.getById(myCollectionDO.getMajorId());
            myCollectionDO.setMajorName(majorDO.getName());
        }
        ModelAndView mav = new ModelAndView("/pages/myCollection/modify");
        model.addAttribute("myCollection", myCollectionDO);
        return mav;
    }

    @PostMapping("/saveOrUpdate")
    public ModelAndView saveOrUpdate(MyCollectionDO param) {
        ModelAndView mav = new ModelAndView("/pages/myCollection/list");
        myCollectionService.saveOrUpdate(param);
        return mav;
    }

}
