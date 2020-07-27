package com.home.examination.mapper;

import com.home.examination.entity.domain.HollandResultDO;
import org.springframework.stereotype.Repository;

@Repository
public interface HollandResultMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(HollandResultDO record);

    int insertSelective(HollandResultDO record);

    HollandResultDO selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(HollandResultDO record);

    int updateByPrimaryKey(HollandResultDO record);

    HollandResultDO selectByCode(String code);

}