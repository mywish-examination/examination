package com.home.examination.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.home.examination.entity.domain.MyCollectionDO;
import com.home.examination.mapper.MyCollectionMapper;
import com.home.examination.service.MyCollectionService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class MyCollectionServiceImpl extends ServiceImpl<MyCollectionMapper, MyCollectionDO> implements MyCollectionService {

    @Resource
    private MyCollectionMapper myCollectionMapper;

    @Override
    public List<MyCollectionDO> pageByQueryWrapper(Wrapper<MyCollectionDO> queryWrapper) {
        return myCollectionMapper.pageByQueryWrapper(queryWrapper);
    }

    @Override
    public int countByQueryWrapper(Wrapper<MyCollectionDO> queryWrapper) {
        return myCollectionMapper.countByQueryWrapper(queryWrapper);
    }

}
