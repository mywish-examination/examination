package com.home.examination.common.enumerate;

import com.alibaba.druid.util.StringUtils;
import com.home.examination.common.runner.MyStartupRunner;
import com.home.examination.entity.domain.DataDictionaryDO;

import java.util.List;

/**
 * 职业测评枚举
 */
public enum HollandEnum {

    R("R", "实际型（Realistic）"),
    I("I", "研究型（Investigative）"),
    A("A", "艺术型（Artistic）"),
    S("S", "社会型（Social）"),
    E("E", "管理型（Enterprising）"),
    C("C", "常规型（Conventional）"),
    ;

    private String code;
    private String message;

    HollandEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

}
