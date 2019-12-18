package com.home.examination.entity.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("news_information")
public class NewsInformationDO extends BaseEntity {

    /**
     * 标题
     */
    String title;
    /**
     * 图片url
     */
    String imageUrl;
    /**
     * 内容
     */
    String content;
}
