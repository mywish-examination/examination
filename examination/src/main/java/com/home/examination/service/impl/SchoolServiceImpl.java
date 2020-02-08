package com.home.examination.service.impl;

import com.alibaba.druid.util.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.home.examination.entity.domain.SchoolDO;
import com.home.examination.mapper.SchoolMapper;
import com.home.examination.service.SchoolService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class SchoolServiceImpl extends ServiceImpl<SchoolMapper, SchoolDO> implements SchoolService {

    @Resource
    private SchoolMapper schoolMapper;

    @Override
    public SchoolDO getByEducationalCode(String educationalCode) {
        if(StringUtils.isEmpty(educationalCode)) return null;

        LambdaQueryWrapper<SchoolDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SchoolDO::getEducationalCode, educationalCode);
        return schoolMapper.selectOne(queryWrapper);
    }

}
