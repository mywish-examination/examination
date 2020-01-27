package com.home.examination.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.home.examination.entity.domain.MajorDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface MajorMapper extends BaseMapper<MajorDO> {

    @Select("select t.*" +
            "from major t " +
            "${ew.customSqlSegment}")
    List<MajorDO> pageByQueryWrapper(@Param("ew") Wrapper<MajorDO> queryWrapper);

    @Select("select count(t.id) " +
            "from major t " +
            "${ew.customSqlSegment}")
    int countByQueryWrapper(@Param("ew") Wrapper<MajorDO> queryWrapper);

}
