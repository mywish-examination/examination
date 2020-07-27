package com.home.examination.mapper;

import com.home.examination.entity.domain.HollandTypeDO;
import org.springframework.stereotype.Repository;

@Repository
public interface HollandTypeMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(HollandTypeDO record);

    int insertSelective(HollandTypeDO record);

    HollandTypeDO selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(HollandTypeDO record);

    int updateByPrimaryKeyWithBLOBs(HollandTypeDO record);

    int updateByPrimaryKey(HollandTypeDO record);
}