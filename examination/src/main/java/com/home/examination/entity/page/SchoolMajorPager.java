package com.home.examination.entity.page;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.home.examination.entity.domain.MajorDO;
import com.home.examination.entity.domain.SchoolMajorDO;
import lombok.Data;
import org.springframework.stereotype.Component;

/**
 * 分页类
 */
@Data
@Component
public class SchoolMajorPager {

    /**
     * 分页信息
     */
    private Page<SchoolMajorDO> pager;
    /**
     * 查询参数
     */
    private SchoolMajorDO requestParam;

    /**
     * token
     */
    private String token;

}