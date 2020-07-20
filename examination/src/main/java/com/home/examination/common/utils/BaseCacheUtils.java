package com.home.examination.common.utils;

import com.home.examination.common.config.ConstantHandler;
import com.home.examination.common.runner.MyStartupRunner;
import com.home.examination.entity.domain.DataDictionaryDO;
import com.home.examination.service.DataDictionaryService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;

@Component
public class BaseCacheUtils {

    @Resource
    private RedisTemplate redisTemplate;

    @Resource
    private DataDictionaryService dataDictionaryService;

    public List<DataDictionaryDO> listDictByCode(String dictCode) {
        String key = ConstantHandler.REDIS_KEY_DATA_DICT + dictCode;
        List<DataDictionaryDO> resultList = (List<DataDictionaryDO>) redisTemplate.opsForList().leftPop(key);
        if(CollectionUtils.isEmpty(resultList)) {
            MyStartupRunner.map.putAll(dataDictionaryService.initList());

            resultList = MyStartupRunner.map.get(dictCode);
        }

        return resultList;
    }

}
