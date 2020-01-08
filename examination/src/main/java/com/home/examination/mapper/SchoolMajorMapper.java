package com.home.examination.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.home.examination.entity.domain.MajorDO;
import com.home.examination.entity.domain.SchoolMajorDO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface SchoolMajorMapper extends BaseMapper<SchoolMajorDO> {

    @Select("select t.* from major t, school_major sc where t.id = sc.major_id and sc.school_id = #{schoolId}")
    List<MajorDO> listMajorBySchoolId(@Param("schoolId") Long schoolId);
}
