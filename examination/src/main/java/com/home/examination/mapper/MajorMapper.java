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

    @Select("select s.name as schoolName, t.*" +
            "from major t left join school_major sm on t.id = sm.major_id " +
            "left join school s on sm.educational_code = s.educational_code " +
            "${ew.customSqlSegment}")
    List<MajorDO> pageByQueryWrapper(@Param("ew") Wrapper<MajorDO> queryWrapper);

    @Select("select count(t.id) " +
            "from major t " +
            "${ew.customSqlSegment}")
    int countByQueryWrapper(@Param("ew") Wrapper<MajorDO> queryWrapper);

    List<MajorDO> listMajor(@Param("educationalCode") String educationalCode, @Param("userId") Long userId, @Param("batchCode") String batchCode, @Param("remark") String remark);

}
