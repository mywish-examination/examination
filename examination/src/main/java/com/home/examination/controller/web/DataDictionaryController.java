package com.home.examination.controller.web;

import com.home.examination.entity.domain.DataDictionaryDO;
import com.home.examination.entity.page.Pager;
import com.home.examination.service.DataDictionaryService;
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
@RequestMapping("/web/dataDictionary")
public class DataDictionaryController {

    @Resource
    private DataDictionaryService dataDictionaryService;

    @PostMapping("/listPage")
    @ResponseBody
    public Pager<DataDictionaryDO> listPage(Pager<DataDictionaryDO> pager) {
        dataDictionaryService.page(pager.getPager());
        return pager;
    }

    @PostMapping("/delete")
    @ResponseBody
    public Map<String, String> delete(Long id) {
        boolean result = dataDictionaryService.removeById(id);
        Map<String, String> map = new HashMap<>();
        map.put("status", result ? "success" : "error");
        return map;
    }

    @GetMapping("/detail")
    public ModelAndView detail(Long id, Model model) {
        DataDictionaryDO dataDictionaryDO = dataDictionaryService.getById(id);
        ModelAndView mav = new ModelAndView("/pages/examinationManager/dataDictionary/modify");
        model.addAttribute("dataDictionary", dataDictionaryDO);
        return mav;
    }

    @PostMapping("/saveOrUpdate")
    public ModelAndView saveOrUpdate(DataDictionaryDO param) {
        ModelAndView mav = new ModelAndView("/pages/examinationManager/dataDictionary/list");
        dataDictionaryService.saveOrUpdate(param);
        return mav;
    }

}
