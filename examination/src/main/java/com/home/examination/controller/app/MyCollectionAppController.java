package com.home.examination.controller.app;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.home.examination.entity.domain.UserDO;
import com.home.examination.entity.vo.ExecuteResult;
import com.home.examination.entity.domain.MyCollectionDO;
import com.home.examination.entity.page.MyCollectionPager;
import com.home.examination.service.MyCollectionService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/app/myCollection")
public class MyCollectionAppController {

    @Resource
    private MyCollectionService myCollectionService;
    @Resource
    private RedisTemplate redisTemplate;

    @PostMapping("/listPage")
    public ExecuteResult listPage(MyCollectionPager pager, String token) {
        UserDO user = (UserDO) redisTemplate.opsForValue().get(token);

        LambdaQueryWrapper<MyCollectionDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(MyCollectionDO::getUserId, user.getId());
        Long total = myCollectionService.countByUserId(queryWrapper);

        queryWrapper.last(String.format("limit %s, %s", pager.getPager().offset(), pager.getPager().getSize()));
        List<MyCollectionDO> myCollectionList = myCollectionService.pageByUserId(queryWrapper);

        pager.getPager().setRecords(myCollectionList);
        pager.getPager().setTotal(total);
        return new ExecuteResult(pager);
    }

    @PostMapping("/delete")
    public ExecuteResult delete(Long id) {
        return new ExecuteResult(myCollectionService.removeById(id));
    }

    @PostMapping("/detail")
    public ExecuteResult detail(Long id) {
        return new ExecuteResult(myCollectionService.getById(id));
    }

    @PostMapping("/collection")
    public ExecuteResult saveOrUpdate(MyCollectionDO param, int type) {
        boolean result;
        if(type == 0) {
            result = myCollectionService.removeById(param.getMajorId());
        } else {
            result = myCollectionService.saveOrUpdate(param);
        }
        return new ExecuteResult(result);
    }

}
