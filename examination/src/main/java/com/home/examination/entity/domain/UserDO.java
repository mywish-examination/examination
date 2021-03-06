package com.home.examination.entity.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.home.examination.common.enumerate.DictCodeEnum;
import com.home.examination.common.runner.MyStartupRunner;
import lombok.Data;

import java.math.BigDecimal;

@Data
@TableName("user")
public class UserDO extends BaseEntity {

    /**
     * 真实姓名
     */
    private String trueName;
    /**
     * 登录名
     */
    private String loginName;
    /**
     * 密码
     */
    private String password;
    /**
     * 省份Id
     */
    private Long provinceId;
    /**
     * 省份名称
     */
    @TableField(exist = false)
    private String provinceName;

    public String getProvinceName() {
        if (this.provinceId == null) return "";
        return MyStartupRunner.list.stream().filter(city -> city.getId() == this.provinceId).map(CityDO::getCityName).findFirst().get();
    }

    /**
     * 民族
     */
    private String nation;

    public String getNationName() {
        return DictCodeEnum.getValueByNum(DictCodeEnum.DICT_USER_NATION.getCode(), this.nation);
    }

    /**
     * 高考年份
     */
    private Integer collegeYears;
    /**
     * 科类
     */
    private String subjectType;

    public String getSubjectTypeName() {
        return DictCodeEnum.getValueByNum(DictCodeEnum.DICT_SUBJECT_TYPE.getCode(), this.subjectType);
    }

    /**
     * 角色类型：0:管理员，1：学生
     */
    private String type;

    public String getTypeName() {
        return DictCodeEnum.getValueByNum(DictCodeEnum.DICT_USER_TYPE.getCode(), this.type);
    }
    // 新增字段
    /**
     * 高考分数
     */
    private BigDecimal collegeScore;

    /**
     * 预估分数
     */
    private BigDecimal predictedScore;

    /**
     * token
     */
    @TableField(exist = false)
    private String token;

    /**
     * 男女
     */
    private String sex;

    public String getSexName() {
        return DictCodeEnum.getValueByNum(DictCodeEnum.DICT_USER_SEX.getCode(), this.sex);
    }

    /**
     * 位次
     */
    @TableField(exist = false)
    private Integer rank;

    /**
     * 序列号
     */
    private String serialNumber;
    /**
     * 授权码
     */
    private String authorizationCode;

}
