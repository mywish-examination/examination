package com.home.examination.entity.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class BaseEntity implements Serializable {

    /**
     * 主键Id
     */
    @TableId(type = IdType.AUTO)
    private Long id;
    /**
     * 创建时间
     */
    private Date createTime;
}
