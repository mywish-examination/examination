package com.home.examination.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.home.examination.entity.domain.SubsectionDO;
import com.home.examination.mapper.SubsectionMapper;
import com.home.examination.service.SubsectionService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class SubsectionServiceImpl extends ServiceImpl<SubsectionMapper, SubsectionDO> implements SubsectionService {

    @Resource
    private SubsectionMapper subsectionMapper;

    @Override
    public int getScore(String subjectType, Integer rank) {
        return subsectionMapper.getScore(subjectType, rank);
    }
}
