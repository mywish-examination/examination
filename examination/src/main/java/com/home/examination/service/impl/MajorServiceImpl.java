package com.home.examination.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.home.examination.entity.domain.MajorDO;
import com.home.examination.entity.domain.SchoolDO;
import com.home.examination.entity.page.Pager;
import com.home.examination.mapper.MajorMapper;
import com.home.examination.mapper.SchoolMapper;
import com.home.examination.service.MajorService;
import com.home.examination.service.SchoolService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class MajorServiceImpl extends ServiceImpl<MajorMapper, MajorDO> implements MajorService {

    @Override
    public Pager<MajorDO> listPage(Pager<MajorDO> pager) {
        List<MajorDO> records = lambdaQuery().page(pager.getPage()).getRecords();

        pager.getPage().setTotal(records.size());
        return pager;
    }

}
