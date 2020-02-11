package com.home.examination.entity.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.home.examination.common.enumerate.DictCodeEnum;
import lombok.Data;

@Data
@TableName("feature")
public class FeatureDO extends BaseEntity {
    /**
     * 特征名称
     */
    private String featureName;
    /**
     * 特征类型
     */
    private String featureType;

    public String getFeatureTypeName() {
        return DictCodeEnum.getValueById(DictCodeEnum.DICT_FEATURE_TYPE.getCode(), this.featureType);
    }

    /**
     * 特征选项
     */
    private String featureOption;

    public String getFeatureOptionName() {
        return DictCodeEnum.getValueById(DictCodeEnum.DICT_FEATURE_OPTION.getCode(), this.featureOption);
    }

}
