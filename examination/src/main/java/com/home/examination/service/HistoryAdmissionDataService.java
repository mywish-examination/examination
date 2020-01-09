package com.home.examination.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.home.examination.entity.domain.HistoryAdmissionDataDO;

import java.util.List;

public interface HistoryAdmissionDataService extends IService<HistoryAdmissionDataDO> {

    List<HistoryAdmissionDataDO> pager(Wrapper<HistoryAdmissionDataDO> queryWrapper);

    int count(Wrapper<HistoryAdmissionDataDO> queryWrapper);

}
