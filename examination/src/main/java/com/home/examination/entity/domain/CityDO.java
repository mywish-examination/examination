package com.home.examination.entity.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("city")
public class CityDO extends BaseEntity {

    /**
     * 父Id
     */
    private Long pid;
    /**
     * 城市名称
     */
    private String cityName;
    /**
     * 类型-0：中国根节点，1：省，2：市，3：区、县
     */
    private String type;

}
