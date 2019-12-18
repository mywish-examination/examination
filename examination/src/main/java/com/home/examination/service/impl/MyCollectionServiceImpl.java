package com.home.examination.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.home.examination.entity.domain.MyCollectionDO;
import com.home.examination.entity.domain.SchoolDO;
import com.home.examination.entity.page.Pager;
import com.home.examination.mapper.MyCollectionMapper;
import com.home.examination.mapper.SchoolMapper;
import com.home.examination.service.MyCollectionService;
import com.home.examination.service.SchoolService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class MyCollectionServiceImpl extends ServiceImpl<MyCollectionMapper, MyCollectionDO> implements MyCollectionService {

    @Override
    public Pager<MyCollectionDO> listPage(Pager<MyCollectionDO> pager) {
        List<MyCollectionDO> records = lambdaQuery().page(pager.getPage()).getRecords();

        pager.getPage().setTotal(records.size());
        return pager;
    }

}
