package com.home.examination.entity.vo;

import com.home.examination.entity.domain.DataDictionaryDO;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class TargetCollegesConfigVO {

    private List<DataDictionaryDO> remarkDictList = new ArrayList<>();
    private List<DataDictionaryDO> batchDictList = new ArrayList<>();

}
