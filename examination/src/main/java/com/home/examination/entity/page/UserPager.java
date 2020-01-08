package com.home.examination.entity.page;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.home.examination.entity.domain.UserDO;
import lombok.Data;
import org.springframework.stereotype.Component;

/**
 * 分页类
 */
@Data
@Component
public class UserPager {

    /**
     * 分页信息
     */
    private Page<UserDO> pager;
    /**
     * 查询参数
     */
    private UserDO requestParam;

}