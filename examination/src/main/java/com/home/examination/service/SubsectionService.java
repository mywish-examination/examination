package com.home.examination.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.home.examination.entity.domain.SubsectionDO;

public interface SubsectionService extends IService<SubsectionDO> {

    int getScore(String subjectType, Integer rank);


}
