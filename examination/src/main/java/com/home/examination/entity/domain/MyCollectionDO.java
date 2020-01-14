package com.home.examination.entity.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 我的收藏
 */
@Data
@TableName("my_collection")
public class MyCollectionDO extends BaseEntity {

    /**
     * 用户Id
     */
    private Long userId;
    /**
     * 专业Id
     */
    private Long majorId;

    /**
     * 专业名称
     */
    @TableField(exist = false)
    private String majorName;
    /**
     * 学校名称
     */
    @TableField(exist = false)
    private String schoolName;

    /**
     * 用户名称
     */
    @TableField(exist = false)
    private String userName;


}
