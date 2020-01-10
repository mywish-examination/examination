package com.home.examination.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.home.examination.entity.domain.MajorDO;

import java.util.List;

public interface MajorService extends IService<MajorDO> {

    List<MajorDO> pageByQueryWrapper(Wrapper<MajorDO> queryWrapper);

    int countByQueryWrapper(Wrapper<MajorDO> queryWrapper);

}
