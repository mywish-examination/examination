package com.home.examination.common.runner;

import com.home.examination.entity.domain.CityDO;
import com.home.examination.entity.domain.DataDictionaryDO;
import com.home.examination.service.CityService;
import com.home.examination.service.DataDictionaryService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Auther: Jhon Li
 * @Date: 2018/12/8 09:57
 * @Description:
 */
@Component
public class MyStartupRunner implements CommandLineRunner {

    public static final Map<String, List<DataDictionaryDO>> map = new HashMap<>();
    public static final List<CityDO> list = new ArrayList<>();

    @Resource
    private DataDictionaryService dataDictionaryService;
    @Resource
    private CityService cityService;

    @Override
    public void run(String... args) {
        list.addAll(cityService.list());
        map.putAll(dataDictionaryService.initList());
    }

}