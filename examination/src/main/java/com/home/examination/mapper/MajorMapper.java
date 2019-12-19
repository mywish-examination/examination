package com.home.examination.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.home.examination.entity.domain.MajorDO;
import com.home.examination.entity.domain.SchoolDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface MajorMapper extends BaseMapper<MajorDO> {

}
