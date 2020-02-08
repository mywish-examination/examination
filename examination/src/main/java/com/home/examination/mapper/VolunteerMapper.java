package com.home.examination.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.home.examination.entity.domain.VolunteerDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface VolunteerMapper extends BaseMapper<VolunteerDO> {

    @Select("select t.*, s.name as schoolName, u.true_name as userName, m.name as majorName " +
            "from volunteer t " +
            "inner join school s on t.educational_code = s.educational_code " +
            "inner join major m on t.major_id = m.id " +
            "inner join user u on t.user_id = u.id " +
            "${ew.customSqlSegment}")
    List<VolunteerDO> pageByQueryWrapper(@Param("ew") Wrapper<VolunteerDO> queryWrapper);

    @Select("select count(t.id) " +
            "from volunteer t " +
            "inner join school s on t.educational_code = s.educational_code " +
            "inner join major m on t.major_id = m.id " +
            "inner join user u on t.user_id = u.id " +
            "${ew.customSqlSegment}")
    int countByQueryWrapper(@Param("ew") Wrapper<VolunteerDO> queryWrapper);

}
