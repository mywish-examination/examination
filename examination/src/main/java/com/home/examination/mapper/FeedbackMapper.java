package com.home.examination.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.home.examination.entity.domain.FeedbackDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface FeedbackMapper extends BaseMapper<FeedbackDO> {

    @Select("select t.*, u.name as userName " +
            "from feedback t " +
            "inner join user u on t.user_id = u.id " +
            "${ew.customSqlSegment}")
    List<FeedbackDO> pageByQueryWrapper(@Param("ew") Wrapper<FeedbackDO> queryWrapper);

    @Select("select count(t.id) " +
            "from feedback t " +
            "inner join user u on t.user_id = u.id " +
            "${ew.customSqlSegment}")
    int countByQueryWrapper(@Param("ew") Wrapper<FeedbackDO> queryWrapper);

}
