package com.home.examination.entity.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.home.examination.common.enumerate.DictCodeEnum;
import com.home.examination.common.runner.MyStartupRunner;
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
        return DictCodeEnum.getValueByNum(DictCodeEnum.DICT_FEATURE_TYPE.getCode(), this.featureType);
    }

    private String featureCode;

    public String getFeatureCodeName() {
        return MyStartupRunner.dictNameMap.get(this.featureCode);
    }

    /**
     * 特征选项
     */
    private String featureOption;

    public String getFeatureOptionName() {
        return DictCodeEnum.getValueByNum(this.featureCode, this.featureOption);
    }

}
