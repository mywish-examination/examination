package com.home.examination.entity.vo;

import com.home.examination.entity.domain.FeatureDO;
import lombok.Data;

import java.util.List;

@Data
public class FeatureVO {

    private String featureCode;
    private String featureName;

    private List<FeatureDO> list;

}
