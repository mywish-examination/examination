package com.home.examination.common.runner;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.home.examination.entity.domain.CityDO;
import com.home.examination.entity.domain.DataDictionaryDO;
import com.home.examination.service.CityService;
import com.home.examination.service.DataDictionaryService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Auther: Jhon Li
 * @Date: 2018/12/8 09:57
 * @Description:
 */
@Component
public class MyStartupRunner implements CommandLineRunner {

    public static final Map<String, List<DataDictionaryDO>> map = new HashMap<>();
    public static final List<CityDO> list = new ArrayList<>();

    public static final Map<String, String> dictNameMap = new HashMap<>();

    @Resource
    private DataDictionaryService dataDictionaryService;
    @Resource
    private CityService cityService;

    @Override
    public void run(String... args) {
        list.addAll(cityService.list());
        map.putAll(dataDictionaryService.initList());

        LambdaQueryWrapper<DataDictionaryDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.select(DataDictionaryDO::getDictCode, DataDictionaryDO::getDictName).last("group by dict_code");
        List<DataDictionaryDO> list = dataDictionaryService.list(queryWrapper);

        Map<String, String> collect = list.stream().collect(Collectors.toMap(DataDictionaryDO::getDictCode, DataDictionaryDO::getDictName));
        dictNameMap.putAll(collect);
    }

}