package com.home.examination.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.home.examination.entity.domain.SchoolMajorDO;
import com.home.examination.entity.vo.ByNameVO;
import com.home.examination.mapper.SchoolMajorMapper;
import com.home.examination.service.SchoolMajorService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class SchoolMajorServiceImpl extends ServiceImpl<SchoolMajorMapper, SchoolMajorDO> implements SchoolMajorService {

    @Resource
    private SchoolMajorMapper schoolMajorMapper;

    @Override
    public List<SchoolMajorDO> pageByQueryWrapper(Wrapper<SchoolMajorDO> queryWrapper) {
        return schoolMajorMapper.pageByQueryWrapper(queryWrapper);
    }

    @Override
    public int countByQueryWrapper(Wrapper<SchoolMajorDO> queryWrapper) {
        return schoolMajorMapper.countByQueryWrapper(queryWrapper);
    }

    @Override
    public List<ByNameVO> listByName(Wrapper<SchoolMajorDO> queryWrapper) {
        return schoolMajorMapper.listByName(queryWrapper);
    }

}
