package com.home.examination.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.home.examination.entity.domain.AreaDO;
import com.home.examination.entity.domain.SchoolDO;
import com.home.examination.entity.page.Pager;
import com.home.examination.mapper.AreaMapper;
import com.home.examination.mapper.SchoolMapper;
import com.home.examination.service.AreaService;
import com.home.examination.service.SchoolService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class AreaServiceImpl extends ServiceImpl<AreaMapper, AreaDO> implements AreaService {

    @Override
    public Pager<AreaDO> listPage(Pager<AreaDO> pager) {
        List<AreaDO> records = lambdaQuery().page(pager.getPage()).getRecords();

        pager.getPage().setTotal(records.size());
        return pager;
    }

}
