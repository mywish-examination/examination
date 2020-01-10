package com.home.examination.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.home.examination.entity.domain.MyCollectionDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface MyCollectionMapper extends BaseMapper<MyCollectionDO> {

    @Select("select t.*, m.name as majorName, s.name as schoolName " +
            "from my_collection t " +
            "inner join major m on t.major_id = m.id " +
            "inner join school s on t.school_id = s.id " +
            "${ew.customSqlSegment}")
    List<MyCollectionDO> pageByQueryWrapper(@Param("ew") Wrapper<MyCollectionDO> queryWrapper);

    @Select("select count(t.id) " +
            "from my_collection t " +
            "inner join major m on t.major_id = m.id " +
            "inner join school s on t.school_id = s.id " +
            "${ew.customSqlSegment}")
    int countByQueryWrapper(@Param("ew") Wrapper<MyCollectionDO> queryWrapper);

}
