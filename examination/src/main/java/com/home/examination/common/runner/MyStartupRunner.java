package com.home.examination.common.runner;

import com.home.examination.entity.domain.DataDictionaryDO;
import com.home.examination.service.DataDictionaryService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

import static java.util.concurrent.TimeUnit.SECONDS;

/**
 * @Auther: Jhon Li
 * @Date: 2018/12/8 09:57
 * @Description:
 */
@Component
public class MyStartupRunner implements CommandLineRunner {

    @Resource
    private DataDictionaryService dataDictionaryService;
    @Resource
    private RedisTemplate redisTemplate;

    @Override
    public void run(String... args) {
        List<DataDictionaryDO> list = dataDictionaryService.list();
        for (DataDictionaryDO dataDictionaryDO : list) {
            redisTemplate.opsForList().leftPush("dataDictionaryList", dataDictionaryDO);
        }
        redisTemplate.expire("dataDictionaryList", 1000, SECONDS);
    }

}