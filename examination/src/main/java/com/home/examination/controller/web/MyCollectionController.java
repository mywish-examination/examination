package com.home.examination.controller.web;

import com.home.examination.entity.domain.MyCollectionDO;
import com.home.examination.entity.domain.SchoolDO;
import com.home.examination.entity.page.Pager;
import com.home.examination.service.MyCollectionService;
import com.home.examination.service.SchoolService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;

@Controller
@RequestMapping("/web/myCollection")
public class MyCollectionController {

    @Resource
    private MyCollectionService myCollectionService;

    @PostMapping("/listPage")
    public ModelAndView listPage(Pager<MyCollectionDO> pager) {
        Pager<MyCollectionDO> schoolPager = myCollectionService.listPage(pager);
        ModelAndView modelAndView = new ModelAndView("/school/schoolList");
        modelAndView.addObject("schoolPager", schoolPager);

        return modelAndView;
    }

}
