package com.home.examination.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.home.examination.entity.domain.MyCollectionDO;

import java.util.List;

public interface MyCollectionService extends IService<MyCollectionDO> {

    List<MyCollectionDO> pageByUserId(Wrapper<MyCollectionDO> queryWrapper);

    Long countByUserId(Wrapper<MyCollectionDO> queryWrapper);

}
