package com.home.examination.controller.web;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.home.examination.entity.domain.MajorDO;
import com.home.examination.entity.domain.SchoolDO;
import com.home.examination.entity.domain.SchoolMajorDO;
import com.home.examination.entity.page.SchoolMajorPager;
import com.home.examination.service.MajorService;
import com.home.examination.service.SchoolMajorService;
import com.home.examination.service.SchoolService;
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
@RequestMapping("/web/schoolMajor")
public class SchoolMajorController {

    @Resource
    private SchoolMajorService schoolMajorService;
    @Resource
    private MajorService majorService;
    @Resource
    private SchoolService schoolService;

    @RequestMapping("/listPage")
    @ResponseBody
    public SchoolMajorPager listPage(SchoolMajorPager pager) {
        LambdaQueryWrapper<SchoolMajorDO> queryWrapper = new LambdaQueryWrapper<>();
        int total = schoolMajorService.countByQueryWrapper(queryWrapper);
        List<SchoolMajorDO> list = Collections.emptyList();
        if (total > 0) {
            queryWrapper.last(String.format("limit %s, %s", pager.getPager().offset(), pager.getPager().getSize()));
            list = schoolMajorService.pageByQueryWrapper(queryWrapper);
        }

        pager.getPager().setTotal(total);
        pager.getPager().setRecords(list);
        return pager;
    }

    @PostMapping("/delete")
    @ResponseBody
    public Map<String, String> delete(Long id) {
        boolean result = schoolMajorService.removeById(id);
        Map<String, String> map = new HashMap<>();
        map.put("status", result ? "success" : "error");
        return map;
    }

    @GetMapping("/detail")
    public ModelAndView detail(SchoolMajorDO major, Model model) {
        SchoolMajorDO schoolMajorDO = new SchoolMajorDO();
        if (major.getId() != null) {
            schoolMajorDO = schoolMajorService.getById(major.getId());

            MajorDO majorDO = majorService.getById(schoolMajorDO.getMajorId());
            schoolMajorDO.setMajorName(majorDO.getName());
        }
        ModelAndView mav = new ModelAndView("/pages/schoolMajor/modify");
        model.addAttribute("schoolMajor", schoolMajorDO);
        return mav;
    }

    @PostMapping("/saveOrUpdate")
    public ModelAndView saveOrUpdate(SchoolMajorDO param) {
        ModelAndView mav = new ModelAndView("/pages/schoolMajor/list");
        schoolMajorService.saveOrUpdate(param);
        return mav;
    }

}
