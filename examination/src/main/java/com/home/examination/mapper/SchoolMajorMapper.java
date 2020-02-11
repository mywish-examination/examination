package com.home.examination.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.home.examination.entity.domain.SchoolMajorDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface SchoolMajorMapper extends BaseMapper<SchoolMajorDO> {

    @Select("select t.*, m.name as majorName, s.name as schoolName " +
            "from school_major t " +
            "inner join school s on t.educational_code = s.educational_code " +
            "inner join major m on t.major_id = m.id " +
            "${ew.customSqlSegment}")
    List<SchoolMajorDO> pageByQueryWrapper(@Param("ew") Wrapper<SchoolMajorDO> queryWrapper);

    @Select("select count(t.id) " +
            "from school_major t " +
            "inner join school s on t.educational_code = s.educational_code " +
            "inner join major m on t.major_id = m.id " +
            "${ew.customSqlSegment}")
    int countByQueryWrapper(@Param("ew") Wrapper<SchoolMajorDO> queryWrapper);

}
