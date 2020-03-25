package com.home.examination.controller.app;

import com.home.examination.common.config.ConstantHandler;
import com.home.examination.entity.domain.DataDictionaryDO;
import com.home.examination.entity.vo.ExecuteResult;
import com.home.examination.service.DataDictionaryService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/app/dataDictionary")
public class DataDictionaryAppController {

    @Resource
    private DataDictionaryService dataDictionaryService;
    @Resource
    private RedisTemplate redisTemplate;

    @PostMapping("/list")
    @ResponseBody
    public ExecuteResult list(DataDictionaryDO requestParam) {
        List<DataDictionaryDO> list = (List<DataDictionaryDO>) redisTemplate.opsForList().leftPop(ConstantHandler.redis_key_data_dict + requestParam.getDictCode());
        if (list == null || list.isEmpty()) {
            list = dataDictionaryService.initAndReturnList(requestParam.getDictCode());
        }
        return new ExecuteResult(list);
    }

}
