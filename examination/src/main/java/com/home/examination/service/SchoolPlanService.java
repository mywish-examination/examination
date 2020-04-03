package com.home.examination.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.home.examination.entity.domain.SchoolPlanDO;

import java.util.List;

public interface SchoolPlanService extends IService<SchoolPlanDO> {

    List<SchoolPlanDO> pageByQueryWrapper(Wrapper<SchoolPlanDO> queryWrapper);

    int countByQueryWrapper(Wrapper<SchoolPlanDO> queryWrapper);

}
