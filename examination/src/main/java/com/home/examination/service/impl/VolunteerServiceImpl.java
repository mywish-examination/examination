package com.home.examination.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.home.examination.entity.domain.VolunteerDO;
import com.home.examination.entity.page.Pager;
import com.home.examination.mapper.SchoolMapper;
import com.home.examination.mapper.VolunteerMapper;
import com.home.examination.service.VolunteerService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class VolunteerServiceImpl extends ServiceImpl<VolunteerMapper, VolunteerDO> implements VolunteerService {

    @Resource
    private SchoolMapper schoolMapper;

    @Override
    public Pager<VolunteerDO> listPage(Pager<VolunteerDO> pager) {
        List<VolunteerDO> records = lambdaQuery().page(pager.getPager()).getRecords();

        pager.getPager().setTotal(records.size());
        return pager;
    }

}
