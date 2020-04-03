package com.home.examination.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.home.examination.entity.domain.HistoryDataControlLineDO;
import com.home.examination.entity.domain.SchoolPlanDO;
import com.home.examination.mapper.HistoryDataControlLineMapper;
import com.home.examination.service.HistoryDataControlLineService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class HistoryDataControlLineServiceImpl extends ServiceImpl<HistoryDataControlLineMapper, HistoryDataControlLineDO> implements HistoryDataControlLineService {

    @Resource
    private HistoryDataControlLineMapper historyDataControlLineMapper;

}
