package com.home.examination.entity.page;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.home.examination.entity.domain.FeatureDO;
import lombok.Data;
import org.springframework.stereotype.Component;

/**
 * 分页类
 */
@Data
@Component
public class FeaturePager {

    /**
     * 分页信息
     */
    private Page<FeatureDO> pager;
    /**
     * 查询参数
     */
    private FeatureDO requestParam;

}