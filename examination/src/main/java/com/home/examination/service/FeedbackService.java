package com.home.examination.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.home.examination.entity.domain.FeedbackDO;
import com.home.examination.entity.domain.VolunteerDO;

import java.util.List;

public interface FeedbackService extends IService<FeedbackDO> {

    List<FeedbackDO> pageByQueryWrapper(Wrapper<FeedbackDO> queryWrapper);

    int countByQueryWrapper(Wrapper<FeedbackDO> queryWrapper);

}
