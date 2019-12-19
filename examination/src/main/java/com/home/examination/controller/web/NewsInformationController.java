package com.home.examination.controller.web;

import com.home.examination.entity.domain.NewsInformationDO;
import com.home.examination.entity.page.Pager;
import com.home.examination.service.NewsInformationService;
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
@RequestMapping("/web/newInformation")
public class NewsInformationController {

    @Resource
    private NewsInformationService newsInformationService;

    @PostMapping("/listPage")
    @ResponseBody
    public Pager<NewsInformationDO> listPage(Pager<NewsInformationDO> pager) {
        newsInformationService.page(pager.getPager());
        return pager;
    }

    @PostMapping("/delete")
    @ResponseBody
    public Map<String, String> delete(Long id) {
        boolean result = newsInformationService.removeById(id);
        Map<String, String> map = new HashMap<>();
        map.put("status", result ? "success" : "error");
        return map;
    }

    @GetMapping("/detail")
    public ModelAndView detail(Long id, Model model) {
        NewsInformationDO newsInformationDO = newsInformationService.getById(id);
        ModelAndView mav = new ModelAndView("/pages/contentInformation/newsInformation/modify");
        model.addAttribute("newsInformation", newsInformationDO);
        return mav;
    }

    @PostMapping("/saveOrUpdate")
    public ModelAndView saveOrUpdate(NewsInformationDO param) {
        ModelAndView mav = new ModelAndView("/pages/contentInformation/newsInformation/list");
        newsInformationService.saveOrUpdate(param);
        return mav;
    }

}
