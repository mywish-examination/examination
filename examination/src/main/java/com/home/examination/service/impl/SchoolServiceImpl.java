package com.home.examination.service.impl;

import com.alibaba.druid.util.StringUtils;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.home.examination.entity.domain.SchoolDO;
import com.home.examination.entity.vo.ByNameVO;
import com.home.examination.mapper.SchoolMapper;
import com.home.examination.service.SchoolService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class SchoolServiceImpl extends ServiceImpl<SchoolMapper, SchoolDO> implements SchoolService {

    @Resource
    private SchoolMapper schoolMapper;

    @Override
    public SchoolDO getByEducationalCode(String educationalCode) {
        if (StringUtils.isEmpty(educationalCode)) return null;

        LambdaQueryWrapper<SchoolDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SchoolDO::getEducationalCode, educationalCode);
        return schoolMapper.selectOne(queryWrapper);
    }

    @Override
    public List<ByNameVO> listByName(Wrapper<SchoolDO> queryWrapper) {
        return schoolMapper.listByName(queryWrapper);
    }

}
