package com.home.examination.entity.page;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.home.examination.entity.domain.FeedbackDO;
import lombok.Data;
import org.springframework.stereotype.Component;

/**
 * 分页类
 */
@Data
@Component
public class FeedbackPager {

    /**
     * 分页信息
     */
    private Page<FeedbackDO> pager;
    /**
     * 查询参数
     */
    private FeedbackDO requestParam;

}