package com.home.examination.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.home.examination.common.config.ConstantHandler;
import com.home.examination.entity.domain.DataDictionaryDO;
import com.home.examination.mapper.DataDictionaryMapper;
import com.home.examination.service.DataDictionaryService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class DataDictionaryServiceImpl extends ServiceImpl<DataDictionaryMapper, DataDictionaryDO> implements DataDictionaryService {

    @Resource
    private RedisTemplate redisTemplate;

    @Override
    public Map<String, List<DataDictionaryDO>> initList() {
        List<DataDictionaryDO> list = this.list();
        Map<String, List<DataDictionaryDO>> collect = list.stream().collect(Collectors.groupingBy(DataDictionaryDO::getDictCode));
        for (Map.Entry<String, List<DataDictionaryDO>> entry :
                collect.entrySet()) {
            redisTemplate.opsForList().leftPush(ConstantHandler.REDIS_KEY_DATA_DICT + entry.getKey(), entry.getValue());
        }
        return collect;
    }

    @Override
    public List<DataDictionaryDO> initAndReturnList(String code) {
        Map<String, List<DataDictionaryDO>> collect = this.list().stream().collect(Collectors.groupingBy(DataDictionaryDO::getDictCode));
        for (Map.Entry<String, List<DataDictionaryDO>> entry :
                collect.entrySet()) {
            redisTemplate.opsForList().leftPush(ConstantHandler.REDIS_KEY_DATA_DICT + entry.getKey(), entry.getValue());
        }

        return collect.get(code);
    }
}
