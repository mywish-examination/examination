package com.home.examination.controller.app;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.home.examination.entity.domain.MajorDO;
import com.home.examination.entity.page.MajorPager;
import com.home.examination.entity.vo.ExecuteResult;
import com.home.examination.service.MajorService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/app/major")
public class MajorAppController {

    @Resource
    private MajorService majorService;

    @RequestMapping("/listPage")
    @ResponseBody
    public ExecuteResult listPage(MajorPager pager) {
        LambdaQueryWrapper<MajorDO> queryWrapper = new LambdaQueryWrapper<>();
        int total = majorService.countByQueryWrapper(queryWrapper);
        List<MajorDO> list = Collections.emptyList();
        if (total > 0) {
            queryWrapper.last(String.format("limit %s, %s", pager.getPager().offset(), pager.getPager().getSize()));
            list = majorService.pageByQueryWrapper(queryWrapper);
        }

        pager.getPager().setRecords(list);
        pager.getPager().setTotal(total);
        return new ExecuteResult(pager);
    }

    @GetMapping("/detail")
    public ExecuteResult detail(Long id) {
        return new ExecuteResult(majorService.getById(id));
    }

}
