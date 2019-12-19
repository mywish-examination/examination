package com.home.examination.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.home.examination.entity.domain.BusinessCooperationDO;
import com.home.examination.entity.page.Pager;
import com.home.examination.mapper.BusinessCooperationMapper;
import com.home.examination.service.BusinessCooperationService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BusinessCooperationServiceImpl extends ServiceImpl<BusinessCooperationMapper, BusinessCooperationDO> implements BusinessCooperationService {

    @Override
    public Pager<BusinessCooperationDO> listPage(Pager<BusinessCooperationDO> pager) {
        List<BusinessCooperationDO> records = lambdaQuery().page(pager.getPager()).getRecords();

        pager.getPager().setTotal(records.size());
        return pager;
    }

}