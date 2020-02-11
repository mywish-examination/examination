package com.home.examination.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.home.examination.entity.domain.HistoryAdmissionDataDO;
import com.home.examination.entity.domain.RankParagraphDO;
import com.home.examination.entity.vo.AdmissionEstimateReferenceDO;

import java.util.List;

public interface HistoryAdmissionDataService extends IService<HistoryAdmissionDataDO> {

    List<HistoryAdmissionDataDO> pageByQueryWrapper(Wrapper<HistoryAdmissionDataDO> queryWrapper);

    int countByQueryWrapper(Wrapper<HistoryAdmissionDataDO> queryWrapper);

    RankParagraphDO getRankParagraphBySchool(String educationalCode, String year);

    AdmissionEstimateReferenceDO getBySchoolOrMajor(Wrapper<HistoryAdmissionDataDO> queryWrapper);

}
