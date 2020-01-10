package com.home.examination.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.home.examination.entity.domain.VolunteerDO;
import com.home.examination.mapper.VolunteerMapper;
import com.home.examination.service.VolunteerService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class VolunteerServiceImpl extends ServiceImpl<VolunteerMapper, VolunteerDO> implements VolunteerService {

    @Resource
    private VolunteerMapper volunteerMapper;

    @Override
    public List<VolunteerDO> pageByQueryWrapper(Wrapper<VolunteerDO> queryWrapper) {
        return volunteerMapper.pageByQueryWrapper(queryWrapper);
    }

    @Override
    public int countByQueryWrapper(Wrapper<VolunteerDO> queryWrapper) {
        return volunteerMapper.countByQueryWrapper(queryWrapper);
    }

}
