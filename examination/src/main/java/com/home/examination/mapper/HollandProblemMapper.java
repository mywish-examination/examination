package com.home.examination.mapper;

import com.home.examination.entity.domain.HollandProblemDO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface HollandProblemMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(HollandProblemDO record);

    int insertSelective(HollandProblemDO record);

    HollandProblemDO selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(HollandProblemDO record);

    int updateByPrimaryKey(HollandProblemDO record);

    List<HollandProblemDO> selectByTypeNumAndPart(Integer typeNum,Integer part);

}