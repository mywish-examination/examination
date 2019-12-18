package com.home.examination.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.home.examination.entity.domain.BusinessCooperationDO;
import com.home.examination.entity.domain.SchoolDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface BusinessCooperationMapper extends BaseMapper<BusinessCooperationDO> {

    /**
     * 获取学校列表
     * @return
     */
    List<BusinessCooperationDO> listPager(@Param("entity") BusinessCooperationDO requestParam);
}
