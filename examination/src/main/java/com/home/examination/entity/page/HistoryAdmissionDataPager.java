package com.home.examination.entity.page;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.home.examination.entity.domain.HistoryAdmissionDataDO;
import lombok.Data;
import org.springframework.stereotype.Component;

/**
 * 分页类
 */
@Data
@Component
public class HistoryAdmissionDataPager {

    /**
     * 分页信息
     */
    private Page<HistoryAdmissionDataDO> pager;
    /**
     * 查询参数
     */
    private HistoryAdmissionDataDO requestParam;

    /**
     * token
     */
    private String token;

}