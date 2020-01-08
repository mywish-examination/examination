package com.home.examination.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.home.examination.entity.domain.HistoryAdmissionDataDO;
import com.home.examination.entity.domain.MajorDO;
import com.home.examination.mapper.HistoryAdmissionDataMapper;
import com.home.examination.mapper.MajorMapper;
import com.home.examination.service.HistoryAdmissionDataService;
import com.home.examination.service.MajorService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class HistoryAdmissionDataServiceImpl extends ServiceImpl<HistoryAdmissionDataMapper, HistoryAdmissionDataDO> implements HistoryAdmissionDataService {

}
