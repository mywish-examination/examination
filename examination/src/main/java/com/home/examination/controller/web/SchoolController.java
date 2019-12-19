package com.home.examination.controller.web;

import com.home.examination.entity.domain.SchoolDO;
import com.home.examination.entity.page.Pager;
import com.home.examination.service.SchoolService;
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
@RequestMapping("/web/school")
public class SchoolController {

    @Resource
    private SchoolService schoolService;

    @PostMapping("/listPage")
    @ResponseBody
    public Pager<SchoolDO> listPage(Pager<SchoolDO> pager) {
        schoolService.page(pager.getPager());
        return pager;
    }

    @PostMapping("/delete")
    @ResponseBody
    public Map<String, String> delete(Long id) {
        boolean result = schoolService.removeById(id);
        Map<String, String> map = new HashMap<>();
        map.put("status", result ? "success" : "error");
        return map;
    }

    @GetMapping("/detail")
    public ModelAndView detail(Long id, Model model) {
        SchoolDO schoolDO = schoolService.getById(id);
        ModelAndView mav = new ModelAndView("/pages/examinationManager/school/modify");
        model.addAttribute("school", schoolDO);
        return mav;
    }

    @PostMapping("/saveOrUpdate")
    public ModelAndView saveOrUpdate(SchoolDO param) {
        ModelAndView mav = new ModelAndView("/pages/examinationManager/school/list");
        schoolService.saveOrUpdate(param);
        return mav;
    }

}
