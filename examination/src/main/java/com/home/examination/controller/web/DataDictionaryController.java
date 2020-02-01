package com.home.examination.controller.web;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.home.examination.common.runner.MyStartupRunner;
import com.home.examination.entity.domain.DataDictionaryDO;
import com.home.examination.entity.page.DataDictionaryPager;
import com.home.examination.service.DataDictionaryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/web/dataDictionary")
public class DataDictionaryController {

    @Resource
    private DataDictionaryService dataDictionaryService;

    @PostMapping("/listPage")
    @ResponseBody
    public DataDictionaryPager listPage(DataDictionaryPager pager) {
        LambdaQueryWrapper<DataDictionaryDO> queryWrapper = new LambdaQueryWrapper<>();
        dataDictionaryService.page(pager.getPager(), queryWrapper);
        return pager;
    }

    @PostMapping("/delete")
    @ResponseBody
    public Map<String, String> delete(Long id) {
        boolean result = dataDictionaryService.removeById(id);
        Map<String, String> map = new HashMap<>();
        map.put("status", result ? "success" : "error");

        // 重新初始化数据字典到redis
        MyStartupRunner.map.clear();
        MyStartupRunner.map.putAll(dataDictionaryService.initList());
        return map;
    }

    @GetMapping("/detail")
    public ModelAndView detail(Long id, Model model) {
        DataDictionaryDO dataDictionaryDO = new DataDictionaryDO();
        if(id != null) {
            dataDictionaryDO = dataDictionaryService.getById(id);
        }

        ModelAndView mav = new ModelAndView("/pages/dataDictionary/modify");
        model.addAttribute("dataDictionary", dataDictionaryDO);
        return mav;
    }

    @PostMapping("/saveOrUpdate")
    public ModelAndView saveOrUpdate(DataDictionaryDO param, HttpServletRequest request) {
        ModelAndView mav = new ModelAndView("/pages/dataDictionary/list");
        dataDictionaryService.saveOrUpdate(param);

        // 重新初始化数据字典到redis
        MyStartupRunner.map.clear();
        MyStartupRunner.map.putAll(dataDictionaryService.initList());

        return mav;
    }

}
