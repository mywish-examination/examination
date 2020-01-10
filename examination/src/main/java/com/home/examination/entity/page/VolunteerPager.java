package com.home.examination.entity.page;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.home.examination.entity.domain.VolunteerDO;
import lombok.Data;
import org.springframework.stereotype.Component;

/**
 * 分页类
 */
@Data
@Component
public class VolunteerPager {

    /**
     * 分页信息
     */
    private Page<VolunteerDO> pager;
    /**
     * 查询参数
     */
    private VolunteerDO requestParam;

    /**
     * token
     */
    private String token;

}