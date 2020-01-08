package com.home.examination.entity.page;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.home.examination.entity.domain.DataDictionaryDO;
import com.home.examination.entity.domain.SchoolDO;
import lombok.Data;
import org.springframework.stereotype.Component;

/**
 * 分页类
 */
@Data
@Component
public class DataDictionaryPager {

    /**
     * 分页信息
     */
    private Page<DataDictionaryDO> pager;
    /**
     * 查询参数
     */
    private DataDictionaryDO requestParam;

}