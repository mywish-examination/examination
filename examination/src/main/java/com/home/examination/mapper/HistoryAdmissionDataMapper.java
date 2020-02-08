package com.home.examination.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.home.examination.entity.domain.HistoryAdmissionDataDO;
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

}
