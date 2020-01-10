package com.home.examination.controller.web;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.home.examination.common.config.DataDictionaryHandler;
import com.home.examination.entity.domain.VolunteerDO;
import com.home.examination.entity.page.VolunteerPager;
import com.home.examination.service.VolunteerService;
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
@RequestMapping("/web/volunteer")
public class VolunteerController {

    @Resource
    private VolunteerService volunteerService;

    @PostMapping("/listPage")
    @ResponseBody
    public VolunteerPager listPage(VolunteerPager pager) {
        LambdaQueryWrapper<VolunteerDO> queryWrapper = new LambdaQueryWrapper<>();
        int total = volunteerService.countByQueryWrapper(queryWrapper);
        List<VolunteerDO> list = Collections.emptyList();
        if (total > 0) {
            queryWrapper.last(String.format("limit %s, %s", pager.getPager().offset(), pager.getPager().getSize()));
            list = volunteerService.pageByQueryWrapper(queryWrapper);
        }

        for (VolunteerDO volunteerDO : list) {
            volunteerDO.setStatus(DataDictionaryHandler.getContentByType("volunteer_status", volunteerDO.getStatus()));
        }

        pager.getPager().setTotal(total);
        pager.getPager().setRecords(list);
        return pager;
    }

    @PostMapping("/delete")
    @ResponseBody
    public Map<String, String> delete(Long id) {
        boolean result = volunteerService.removeById(id);
        Map<String, String> map = new HashMap<>();
        map.put("status", result ? "success" : "error");
        return map;
    }

    @GetMapping("/detail")
    public ModelAndView detail(Long id, Model model) {
        VolunteerDO volunteerDO = volunteerService.getById(id);
        ModelAndView mav = new ModelAndView("/pages/volunteer/modify");
        model.addAttribute("volunteer", volunteerDO);
        return mav;
    }

    @PostMapping("/saveOrUpdate")
    public ModelAndView saveOrUpdate(VolunteerDO param) {
        ModelAndView mav = new ModelAndView("/pages/volunteer/list");
        volunteerService.saveOrUpdate(param);
        return mav;
    }

}
