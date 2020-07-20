package com.home.examination.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.home.examination.entity.domain.SubsectionDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface SubsectionMapper extends BaseMapper<SubsectionDO> {


    @Select("select t.score from subsection t " +
            "where t.year = YEAR(DATE_SUB(now(),INTERVAL 1 YEAR)) " +
            "and t.rank <= #{rank} and t.subject_type = #{subjectType} " +
            "ORDER by t.rank DESC limit 1;")
    int getScore(@Param("subjectType") String subjectType, @Param("rank") Integer rank);

}
