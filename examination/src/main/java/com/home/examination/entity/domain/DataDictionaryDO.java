package com.home.examination.entity.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("data_dictionary")
public class DataDictionaryDO extends BaseEntity {
    /**
     * 字典编号
     */
    private String dictCode;
    /**
     * 字典名称
     */
    private String dictName;
    /**
     * 字典值
     */
    private String dictValue;
    /**
     * 字典数值
     */
    private String dictNum;

}
