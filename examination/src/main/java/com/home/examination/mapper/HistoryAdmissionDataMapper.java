package com.home.examination.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.home.examination.entity.domain.HistoryAdmissionDataDO;
import com.home.examination.entity.domain.RankParagraphDO;
import com.home.examination.entity.vo.AdmissionEstimateReferenceDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface HistoryAdmissionDataMapper extends BaseMapper<HistoryAdmissionDataDO> {

    @Select("select t.*, s.name as schoolName " +
            "from history_admission_data t " +
            "inner join major m on t.major_id = m.id " +
            "inner join school s on t.educational_code = s.educational_code " +
            "${ew.customSqlSegment}")
    List<HistoryAdmissionDataDO> pageByQueryWrapper(@Param("ew") Wrapper<HistoryAdmissionDataDO> queryWrapper);

    @Select("select count(t.id) " +
            "from history_admission_data t " +
            "inner join major m on t.major_id = m.id " +
            "inner join school s on t.educational_code = s.educational_code " +
            "${ew.customSqlSegment}")
    int countByQueryWrapper(@Param("ew") Wrapper<HistoryAdmissionDataDO> queryWrapper);

    @Select("select " +
            "concat(min(t.minimum_rank), '-', max(t.minimum_rank)) as minimumRankParagraph," +
            "concat(min(t.avg_rank), '-', max(t.avg_rank)) as avgRankParagraph," +
            "concat(min(t.highest_rank), '-', max(t.highest_rank)) as highestRankParagraph" +
            "from history_admission_data t " +
            "where t.educational_code = #{educationalCode} " +
            "and t.years >= #{year} GROUP BY t.years")
    RankParagraphDO getRankParagraphBySchool(@Param("educationalCode") String educationalCode, @Param("year") String year);

    @Select("select " +
            "CONCAT(min(t.minimum_rank), '-', max(t.highest_rank)) as rankParagraph, " +
            "CONCAT(min(t.minimum_score), '-', max(t.highest_score)) as scoreParagraph " +
            "from history_admission_data t " +
            "${ew.customSqlSegment}")
    AdmissionEstimateReferenceDO getBySchoolOrMajor(@Param("ew") Wrapper<HistoryAdmissionDataDO> queryWrapper);

}
