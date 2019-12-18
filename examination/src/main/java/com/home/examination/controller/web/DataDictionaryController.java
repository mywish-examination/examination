package com.home.examination.controller.web;

import com.home.examination.entity.domain.DataDictionaryDO;
import com.home.examination.entity.domain.SchoolDO;
import com.home.examination.entity.page.Pager;
import com.home.examination.service.DataDictionaryService;
import com.home.examination.service.SchoolService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;

@Controller
@RequestMapping("/web/dataDictionary")
public class DataDictionaryController {

    @Resource
    private DataDictionaryService dataDictionaryService;

    @PostMapping("/listPage")
    public ModelAndView listPage(Pager<DataDictionaryDO> pager) {
        Pager<DataDictionaryDO> schoolPager = dataDictionaryService.listPage(pager);
        ModelAndView modelAndView = new ModelAndView("/school/schoolList");
        modelAndView.addObject("schoolPager", schoolPager);

        return modelAndView;
    }

}
