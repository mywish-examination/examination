package com.home.examination.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.home.examination.entity.domain.HistoryAdmissionDataDO;
import com.home.examination.entity.domain.SchoolPlanDO;
import com.home.examination.mapper.SchoolPlanMapper;
import com.home.examination.service.SchoolPlanService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class SchoolPlanServiceImpl extends ServiceImpl<SchoolPlanMapper, SchoolPlanDO> implements SchoolPlanService {

    @Resource
    private SchoolPlanMapper schoolPlanMapper;

    @Override
    public List<SchoolPlanDO> pageByQueryWrapper(Wrapper<SchoolPlanDO> queryWrapper) {
        return schoolPlanMapper.pageByQueryWrapper(queryWrapper);
    }

    @Override
    public int countByQueryWrapper(Wrapper<SchoolPlanDO> queryWrapper) {
        return schoolPlanMapper.countByQueryWrapper(queryWrapper);
    }

}
