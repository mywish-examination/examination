package com.home.examination.controller.web;

import com.home.examination.entity.domain.BusinessCooperationDO;
import com.home.examination.entity.page.BusinessCooperationPager;
import com.home.examination.service.BusinessCooperationService;
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
@RequestMapping("/web/businessCooperation")
public class BusinessCooperationController {

    @Resource
    private BusinessCooperationService businessCooperationService;

    @PostMapping("/listPage")
    @ResponseBody
    public BusinessCooperationPager listPage(BusinessCooperationPager pager) {
        businessCooperationService.page(pager.getPager());
        return pager;
    }

    @PostMapping("/delete")
    @ResponseBody
    public Map<String, String> delete(Long id) {
        boolean result = businessCooperationService.removeById(id);
        Map<String, String> map = new HashMap<>();
        map.put("status", result ? "success" : "error");
        return map;
    }

    @GetMapping("/detail")
    public ModelAndView detail(Long id, Model model) {
        BusinessCooperationDO businessCooperationDO = businessCooperationService.getById(id);
        ModelAndView mav = new ModelAndView("/pages/contentInformation/businessCooperation/modify");
        model.addAttribute("businessCooperation", businessCooperationDO == null ? new BusinessCooperationDO() : businessCooperationDO);
        return mav;
    }

    @PostMapping("/saveOrUpdate")
    public ModelAndView saveOrUpdate(BusinessCooperationDO param) {
        ModelAndView mav = new ModelAndView("/pages/contentInformation/businessCooperation/list");
        businessCooperationService.saveOrUpdate(param);
        return mav;
    }

}
