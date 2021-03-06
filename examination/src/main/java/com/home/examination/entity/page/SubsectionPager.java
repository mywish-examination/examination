package com.home.examination.entity.page;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.home.examination.entity.domain.SubsectionDO;
import lombok.Data;
import org.springframework.stereotype.Component;

/**
 * 分页类
 */
@Data
@Component
public class SubsectionPager {

    /**
     * 分页信息
     */
    private Page<SubsectionDO> pager;
    /**
     * 查询参数
     */
    private SubsectionDO requestParam;

    /**
     * token
     */
    private String token;

}