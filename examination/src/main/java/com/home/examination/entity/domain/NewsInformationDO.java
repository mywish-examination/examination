package com.home.examination.entity.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("news_information")
public class NewsInformationDO extends BaseEntity {

    /**
     * 标题
     */
    private String title;
    /**
     * 图片url
     */
    private String imageUrl;
    /**
     * 内容
     */
    private String content;
}
