package com.home.examination.entity.page;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.home.examination.entity.domain.MajorDO;
import lombok.Data;
import org.springframework.stereotype.Component;

/**
 * 分页类
 */
@Data
@Component
public class MajorPager {

    /**
     * 分页信息
     */
    private Page<MajorDO> pager;
    /**
     * 查询参数
     */
    private MajorDO requestParam;

}