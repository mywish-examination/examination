package com.home.examination.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.home.examination.entity.domain.HistoryAdmissionDataDO;
import com.home.examination.entity.domain.RankParagraphDO;
import com.home.examination.entity.domain.SchoolPlanDO;
import com.home.examination.entity.vo.AdmissionEstimateReferenceDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface SchoolPlanMapper extends BaseMapper<SchoolPlanDO> {

    @Select("select t.*, s.name as schoolName " +
            "from school_plan t " +
            "left join school s on t.educational_code = s.educational_code " +
            "${ew.customSqlSegment}")
    List<SchoolPlanDO> pageByQueryWrapper(@Param("ew") Wrapper<SchoolPlanDO> queryWrapper);

    @Select("select count(t.id) " +
            "from school_plan t " +
            "left join school s on t.educational_code = s.educational_code " +
            "${ew.customSqlSegment}")
    int countByQueryWrapper(@Param("ew") Wrapper<SchoolPlanDO> queryWrapper);

    @Select("select IFNULL(sum(t.plan_num), 0) from school_plan t where t.school_name = #{schoolName} and t.major_name = #{majorName}")
    int getPlanNum(@Param("schoolName") String schoolName, @Param("majorName") String majorName);

}
