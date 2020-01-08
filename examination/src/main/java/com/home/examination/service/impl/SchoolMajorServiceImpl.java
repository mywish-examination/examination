package com.home.examination.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.home.examination.entity.domain.MajorDO;
import com.home.examination.entity.domain.SchoolMajorDO;
import com.home.examination.mapper.SchoolMajorMapper;
import com.home.examination.mapper.SchoolMapper;
import com.home.examination.service.SchoolMajorService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class SchoolMajorServiceImpl extends ServiceImpl<SchoolMajorMapper, SchoolMajorDO> implements SchoolMajorService {

    @Resource
    private SchoolMajorMapper schoolMajorMapper;

    @Override
    public List<MajorDO> listMajorBySchoolId(Long schoolId) {
        return schoolMajorMapper.listMajorBySchoolId(schoolId);
    }

}
