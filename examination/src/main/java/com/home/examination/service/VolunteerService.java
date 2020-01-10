package com.home.examination.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.home.examination.entity.domain.MajorDO;
import com.home.examination.entity.domain.VolunteerDO;

import java.util.List;

public interface VolunteerService extends IService<VolunteerDO> {

    List<VolunteerDO> pageByQueryWrapper(Wrapper<VolunteerDO> queryWrapper);

    int countByQueryWrapper(Wrapper<VolunteerDO> queryWrapper);

}
