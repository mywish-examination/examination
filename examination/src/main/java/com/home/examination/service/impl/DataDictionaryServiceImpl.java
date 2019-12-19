package com.home.examination.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.home.examination.entity.domain.DataDictionaryDO;
import com.home.examination.entity.page.Pager;
import com.home.examination.mapper.DataDictionaryMapper;
import com.home.examination.service.DataDictionaryService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DataDictionaryServiceImpl extends ServiceImpl<DataDictionaryMapper, DataDictionaryDO> implements DataDictionaryService {

    @Override
    public Pager<DataDictionaryDO> listPage(Pager<DataDictionaryDO> pager) {
        List<DataDictionaryDO> records = lambdaQuery().page(pager.getPager()).getRecords();

        pager.getPager().setTotal(records.size());
        return pager;
    }

}
