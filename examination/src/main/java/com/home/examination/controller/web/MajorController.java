package com.home.examination.controller.web;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.home.examination.entity.domain.HistoryAdmissionDataDO;
import com.home.examination.entity.domain.MajorDO;
import com.home.examination.entity.page.MajorPager;
import com.home.examination.service.MajorService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/web/major")
public class MajorController {

    @Resource
    private MajorService majorService;

    @RequestMapping("/listPage")
    @ResponseBody
    public MajorPager listPage(MajorPager pager) {
        LambdaQueryWrapper<MajorDO> queryWrapper = new LambdaQueryWrapper<>();
        int total = majorService.countByQueryWrapper(queryWrapper);
        List<MajorDO> list = Collections.emptyList();
        if (total > 0) {
            queryWrapper.last(String.format("limit %s, %s", pager.getPager().offset(), pager.getPager().getSize()));
            list = majorService.pageByQueryWrapper(queryWrapper);
        }

        pager.getPager().setTotal(total);
        pager.getPager().setRecords(list);
        return pager;
    }

    @PostMapping("/delete")
    @ResponseBody
    public Map<String, String> delete(Long id) {
        boolean result = majorService.removeById(id);
        Map<String, String> map = new HashMap<>();
        map.put("status", result ? "success" : "error");
        return map;
    }

    @GetMapping("/detail")
    public ModelAndView detail(Long id, Model model) {
        MajorDO majorDO = majorService.getById(id);
        ModelAndView mav = new ModelAndView("/pages/major/modify");
        model.addAttribute("major", majorDO == null ? new MajorDO() : majorDO);
        return mav;
    }

    @PostMapping("/saveOrUpdate")
    public ModelAndView saveOrUpdate(MajorDO param) {
        ModelAndView mav = new ModelAndView("/pages/major/list");
        majorService.saveOrUpdate(param);
        return mav;
    }

}
