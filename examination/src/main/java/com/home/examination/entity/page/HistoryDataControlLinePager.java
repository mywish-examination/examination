package com.home.examination.entity.page;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.home.examination.entity.domain.HistoryDataControlLineDO;
import lombok.Data;
import org.springframework.stereotype.Component;

/**
 * 分页类
 */
@Data
@Component
public class HistoryDataControlLinePager {

    /**
     * 分页信息
     */
    private Page<HistoryDataControlLineDO> pager;
    /**
     * 查询参数
     */
    private HistoryDataControlLineDO requestParam;

}