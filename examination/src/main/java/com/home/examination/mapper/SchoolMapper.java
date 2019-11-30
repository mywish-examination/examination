package com.home.examination.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.home.examination.entity.domain.SchoolDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SchoolMapper extends BaseMapper<SchoolDO> {

    /**
     * 获取学校列表
     * @return
     */
    List<SchoolDO> listPagerSchool(@Param("entity") SchoolDO requestParam);
}
