package com.home.examination.controller.app;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.home.examination.entity.domain.UserDO;
import com.home.examination.entity.domain.VolunteerDO;
import com.home.examination.entity.page.VolunteerPager;
import com.home.examination.entity.vo.ExecuteResult;
import com.home.examination.service.VolunteerService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/app/volunteer")
public class VolunteerAppController {

    @Resource
    private VolunteerService volunteerService;
    @Resource
    private RedisTemplate redisTemplate;

    @PostMapping("/listPage")
    public ExecuteResult listPage(VolunteerPager pager) {
        UserDO user = (UserDO) redisTemplate.opsForValue().get(pager.getToken());

        LambdaQueryWrapper<VolunteerDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(VolunteerDO::getUserId, user.getId());
        int total = volunteerService.countByQueryWrapper(queryWrapper);
        List<VolunteerDO> list = Collections.emptyList();
        if (total > 0) {
            queryWrapper.last(String.format("limit %s, %s", pager.getPager().offset(), pager.getPager().getSize()));
            list = volunteerService.pageByQueryWrapper(queryWrapper);
        }

        pager.getPager().setRecords(list);
        pager.getPager().setTotal(total);
        return new ExecuteResult(pager);
    }

    @PostMapping("/delete")
    public ExecuteResult delete(Long id) {
        volunteerService.removeById(id);
        return new ExecuteResult();
    }

    @GetMapping("/detail")
    public ExecuteResult detail(Long id) {
        return new ExecuteResult(volunteerService.getById(id));
    }

    @PostMapping("/saveOrUpdate")
    public ExecuteResult saveOrUpdate(VolunteerDO param) {
        return new ExecuteResult(volunteerService.saveOrUpdate(param));
    }

}
