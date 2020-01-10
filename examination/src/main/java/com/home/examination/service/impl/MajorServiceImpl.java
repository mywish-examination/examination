package com.home.examination.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.home.examination.entity.domain.MajorDO;
import com.home.examination.mapper.MajorMapper;
import com.home.examination.service.MajorService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class MajorServiceImpl extends ServiceImpl<MajorMapper, MajorDO> implements MajorService {

    @Resource
    private MajorMapper majorMapper;

    @Override
    public List<MajorDO> pageByQueryWrapper(Wrapper<MajorDO> queryWrapper) {
        return majorMapper.pageByQueryWrapper(queryWrapper);
    }

    @Override
    public int countByQueryWrapper(Wrapper<MajorDO> queryWrapper) {
        return majorMapper.countByQueryWrapper(queryWrapper);
    }

}
