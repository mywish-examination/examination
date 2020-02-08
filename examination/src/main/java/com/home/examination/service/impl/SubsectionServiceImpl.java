package com.home.examination.service.impl;

import com.alibaba.druid.util.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.home.examination.entity.domain.SchoolDO;
import com.home.examination.entity.domain.SubsectionDO;
import com.home.examination.mapper.SchoolMapper;
import com.home.examination.mapper.SubsectionMapper;
import com.home.examination.service.SchoolService;
import com.home.examination.service.SubsectionService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class SubsectionServiceImpl extends ServiceImpl<SubsectionMapper, SubsectionDO> implements SubsectionService {

}
