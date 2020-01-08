package com.home.examination.controller.app;

import com.alibaba.druid.util.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.home.examination.entity.domain.SchoolDO;
import com.home.examination.entity.page.SchoolPager;
import com.home.examination.service.SchoolService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/app/school")
public class SchoolAppController {

    @Resource
    private SchoolService schoolService;

    @PostMapping("/listPage")
    public Map<String, Object> listPage(SchoolPager pager) {
        Map<String, Object> map = new HashMap<>();
        SchoolDO school = pager.getRequestParam();
        LambdaQueryWrapper<SchoolDO> queryWrapper = new LambdaQueryWrapper<>();
        if (school != null) {
            queryWrapper.apply(!StringUtils.isEmpty(school.getName()), " name like '%" + school.getName() + "%'");
        }
        IPage<SchoolDO> page = schoolService.page(pager.getPager(), queryWrapper);
        map.put("page", page);
        map.put("status", "success");

        return map;
    }

    @PostMapping("/detail")
    public Map<String, Object> detail(Long id) {
        Map<String, Object> map = new HashMap<>();

        map.put("school", schoolService.getById(id));
        map.put("status", "success");
        return map;
    }

}
