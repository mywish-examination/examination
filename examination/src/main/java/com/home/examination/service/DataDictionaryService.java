package com.home.examination.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.home.examination.entity.domain.DataDictionaryDO;

import java.util.List;
import java.util.Map;

public interface DataDictionaryService extends IService<DataDictionaryDO> {

    /**
     * 初始数据字典列表
     */
    Map<String, List<DataDictionaryDO>> initList();

    /**
     * 初始化数据字典并返回列表
     */
    List<DataDictionaryDO> initAndReturnList(String code);

}
