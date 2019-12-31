package com.home.examination.entity.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.net.ContentHandler;

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
     * 省份
     */
    private String province;
    /**
     * 地域
     */
    private String area;
    /**
     * 民族
     */
    private String nation;
    /**
     * 高考年份
     */
    private String collegeYears;
    /**
     * 科类
     */
    private String subjectType;
    /**
     * 角色类型：0:管理员，1：学生
     */
    private String type;

    // 新增字段
    /**
     * 高考分数
     */
    private String collegeScore;

    /**
     * 预估分数
     */
    private String predictedScore;

    /**
     * token
     */
    @TableField(exist = false)
    private String token;

}
