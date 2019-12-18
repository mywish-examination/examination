package com.home.examination.entity.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 我的收藏
 */
@Data
@TableName("my_collection")
public class MyCollectionDO extends BaseEntity {

    /**
     * 学校名称
     */
    private String schoolName;
    /**
     * 学校Id
     */
    private Long schoolId;
    /**
     * 用户Id
     */
    private Long userId;

}
