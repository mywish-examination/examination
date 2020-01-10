package com.home.examination.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.home.examination.entity.domain.HistoryAdmissionDataDO;
import com.home.examination.mapper.HistoryAdmissionDataMapper;
import com.home.examination.service.HistoryAdmissionDataService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class HistoryAdmissionDataServiceImpl extends ServiceImpl<HistoryAdmissionDataMapper, HistoryAdmissionDataDO> implements HistoryAdmissionDataService {

    @Resource
    private HistoryAdmissionDataMapper historyAdmissionDataMapper;

    @Override
    public List<HistoryAdmissionDataDO> pageByQueryWrapper(Wrapper<HistoryAdmissionDataDO> queryWrapper) {
        return historyAdmissionDataMapper.pageByQueryWrapper(queryWrapper);
    }

    @Override
    public int countByQueryWrapper(Wrapper<HistoryAdmissionDataDO> queryWrapper) {
        return historyAdmissionDataMapper.countByQueryWrapper(queryWrapper);
    }
}
