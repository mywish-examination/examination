package com.home.examination.controller.web;

import com.home.examination.entity.domain.MajorDO;
import com.home.examination.entity.domain.SchoolDO;
import com.home.examination.entity.domain.SchoolMajorDO;
import com.home.examination.entity.page.Pager;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/web/schoolMajor")
public class SchoolMajorController {

    @Resource
    private SchoolMajorService schoolMajorService;
    @Resource
    private SchoolService schoolService;
    @Resource
    private MajorService majorService;

    @PostMapping("/listPage")
    @ResponseBody
    public Pager<SchoolMajorDO> listPage(Pager<SchoolMajorDO> pager) {
        schoolMajorService.page(pager.getPager());
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
    public ModelAndView detail(Long id, Model model) {
        SchoolMajorDO schoolMajorDO = schoolMajorService.getById(id);
        ModelAndView mav = new ModelAndView("/pages/examinationManager/schoolMajor/modify");
        model.addAttribute("schoolMajor", schoolMajorDO == null ? new SchoolMajorDO() : schoolMajorDO);

        List<SchoolDO> schoolList = schoolService.list();
        List<MajorDO> majorList = majorService.list();
        mav.addObject("schoolList", schoolList);
        mav.addObject("majorList", majorList);

        model.addAttribute("schoolList", schoolList);
        model.addAttribute("majorList", majorList);

        return mav;
    }

    @PostMapping("/saveOrUpdate")
    public ModelAndView saveOrUpdate(SchoolMajorDO param) {
        ModelAndView mav = new ModelAndView("/pages/examinationManager/schoolMajor/list");
        schoolMajorService.saveOrUpdate(param);
        return mav;
    }

}
