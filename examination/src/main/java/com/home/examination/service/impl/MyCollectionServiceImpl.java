package com.home.examination.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.home.examination.entity.domain.MyCollectionDO;
import com.home.examination.entity.page.Pager;
import com.home.examination.mapper.MyCollectionMapper;
import com.home.examination.service.MyCollectionService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MyCollectionServiceImpl extends ServiceImpl<MyCollectionMapper, MyCollectionDO> implements MyCollectionService {

    @Override
    public Pager<MyCollectionDO> listPage(Pager<MyCollectionDO> pager) {
        List<MyCollectionDO> records = lambdaQuery().page(pager.getPager()).getRecords();

        pager.getPager().setTotal(records.size());
        return pager;
    }

}
