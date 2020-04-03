package com.home.examination.entity.page;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.home.examination.entity.domain.SchoolDO;
import com.home.examination.entity.domain.SchoolPlanDO;
import lombok.Data;
import org.springframework.stereotype.Component;

/**
 * 分页类
 */
@Data
@Component
public class SchoolPlanPager {

    /**
     * 分页信息
     */
    private Page<SchoolPlanDO> pager;
    /**
     * 查询参数
     */
    private SchoolPlanDO requestParam;

}