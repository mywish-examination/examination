package com.home.examination.entity.page;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.Data;
import org.springframework.stereotype.Component;

/**
 * 分页类
 * @param <T> 要分页的对象
 */
@Data
@Component
public class Pager<T>{

    /**
     * 分页信息
     */
    private Page<T> page;
    /**
     * 查询参数
     */
    private T requestParam;
}
