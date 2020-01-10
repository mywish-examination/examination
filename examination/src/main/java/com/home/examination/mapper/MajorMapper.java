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

    @Select("select t.*, s.name as schoolName " +
            "from major t " +
            "inner join school s on t.school_id = s.id " +
            "${ew.customSqlSegment}")
    List<MajorDO> pageByQueryWrapper(@Param("ew") Wrapper<MajorDO> queryWrapper);

    @Select("select count(t.id) " +
            "from major t " +
            "inner join school s on t.school_id = s.id " +
            "${ew.customSqlSegment}")
    int countByQueryWrapper(@Param("ew") Wrapper<MajorDO> queryWrapper);

}
