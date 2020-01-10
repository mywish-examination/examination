package com.home.examination.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.home.examination.entity.domain.HistoryAdmissionDataDO;

import java.util.List;

public interface HistoryAdmissionDataService extends IService<HistoryAdmissionDataDO> {

    List<HistoryAdmissionDataDO> pageByQueryWrapper(Wrapper<HistoryAdmissionDataDO> queryWrapper);

    int countByQueryWrapper(Wrapper<HistoryAdmissionDataDO> queryWrapper);

}
