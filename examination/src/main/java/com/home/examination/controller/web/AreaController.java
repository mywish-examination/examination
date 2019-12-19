package com.home.examination.controller.web;

import com.home.examination.entity.domain.AreaDO;
import com.home.examination.entity.page.Pager;
import com.home.examination.service.AreaService;
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
@RequestMapping("/web/area")
public class AreaController {

    @Resource
    private AreaService areaService;

    @PostMapping("/listPage")
    @ResponseBody
    public Pager<AreaDO> listPage(Pager<AreaDO> pager) {
        areaService.page(pager.getPager());
        return pager;
    }

    @PostMapping("/delete")
    @ResponseBody
    public Map<String, String> delete(Long id) {
        boolean result = areaService.removeById(id);
        Map<String, String> map = new HashMap<>();
        map.put("status", result ? "success" : "error");
        return map;
    }

    @GetMapping("/detail")
    public ModelAndView detail(Long id, Model model) {
        AreaDO areaDO = areaService.getById(id);
        ModelAndView mav = new ModelAndView("/pages/examinationManager/area/modify");
        model.addAttribute("area", areaDO);
        return mav;
    }

    @PostMapping("/saveOrUpdate")
    public ModelAndView saveOrUpdate(AreaDO param) {
        ModelAndView mav = new ModelAndView("/pages/examinationManager/area/list");
        areaService.saveOrUpdate(param);
        return mav;
    }

}
