package com.home.examination.entity.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 意见反馈
 */
@Data
@TableName("feedback")
public class FeedbackDO extends BaseEntity {
    /**
     * 意见反馈内容
     */
    private String content;
    /**
     * 用户Id
     */
    private Long userId;

    /**
     * 用户名称
     */
    @TableField(exist = false)
    private String userName;
}
