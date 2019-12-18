package com.home.examination.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.home.examination.entity.domain.NewsInformationDO;
import com.home.examination.entity.domain.SchoolDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface NewsInformationMapper extends BaseMapper<NewsInformationDO> {

    /**
     * 获取学校列表
     * @return
     */
    List<NewsInformationDO> listPager(@Param("entity") NewsInformationDO requestParam);
}
