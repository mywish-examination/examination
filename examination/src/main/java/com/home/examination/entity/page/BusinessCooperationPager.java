package com.home.examination.entity.page;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.home.examination.entity.domain.BusinessCooperationDO;
import lombok.Data;
import org.springframework.stereotype.Component;

/**
 * 分页类
 */
@Data
@Component
@Deprecated
public class BusinessCooperationPager {

    /**
     * 分页信息
     */
    private Page<BusinessCooperationDO> pager;
    /**
     * 查询参数
     */
    private BusinessCooperationDO requestParam;

}