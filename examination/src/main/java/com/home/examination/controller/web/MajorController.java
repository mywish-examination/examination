package com.home.examination.controller.web;

import com.home.examination.entity.domain.MajorDO;
import com.home.examination.entity.domain.SchoolMajorDO;
import com.home.examination.entity.page.Pager;
import com.home.examination.service.MajorService;
import com.home.examination.service.SchoolMajorService;
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
@RequestMapping("/web/major")
public class MajorController {

    @Resource
    private MajorService majorService;
    @Resource
    private SchoolMajorService schoolMajorService;

    @PostMapping("/listPage")
    @ResponseBody
    public Pager<MajorDO> listPage(Pager<MajorDO> pager) {
        majorService.page(pager.getPager());
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
        ModelAndView mav = new ModelAndView("/pages/examinationManager/major/modify");
        model.addAttribute("major", majorDO);
        return mav;
    }

    @PostMapping("/saveOrUpdate")
    public ModelAndView saveOrUpdate(MajorDO param) {
        ModelAndView mav = new ModelAndView("/pages/examinationManager/major/list");
        majorService.saveOrUpdate(param);
        return mav;
    }

}
