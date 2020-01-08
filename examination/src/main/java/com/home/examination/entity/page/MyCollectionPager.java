package com.home.examination.entity.page;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.home.examination.entity.domain.MyCollectionDO;
import lombok.Data;
import org.springframework.stereotype.Component;

/**
 * 分页类
 */
@Data
@Component
public class MyCollectionPager {

    /**
     * 分页信息
     */
    private Page<MyCollectionDO> pager;
    /**
     * 查询参数
     */
    private MyCollectionDO requestParam;

}