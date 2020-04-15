package com.home.examination.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.home.examination.entity.domain.MajorDO;
import com.home.examination.entity.domain.SchoolDO;
import com.home.examination.entity.vo.ByNameVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface SchoolMapper extends BaseMapper<SchoolDO> {

    @Select("select distinct t.educational_code as id, t.name as name " +
            "from school t inner join history_admission_data had on t.educational_code = had.educational_code " +
            "${ew.customSqlSegment}")
    List<ByNameVO> listByName(@Param("ew") Wrapper<SchoolDO> queryWrapper);

    @Select("select s.*, IFNULL(mc.id, 0) as collectionStatus " +
            "from school_major sm inner join school s on " +
            "sm.educational_code = s.educational_code " +
            "left join my_collection mc on sm.major_id = mc.major_id " +
            "and sm.educational_code = mc.educational_code and mc.user_id = #{userId} " +
            "where sm.major_id = #{majorId}")
    List<SchoolDO> listSchool(@Param("userId") Long userId, @Param("majorId") Long majorId);

}
