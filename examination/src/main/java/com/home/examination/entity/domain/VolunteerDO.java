package com.home.examination.entity.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.home.examination.common.enumerate.DictCodeEnum;
import lombok.Data;

import java.math.BigDecimal;

@Data
@TableName("volunteer")
public class VolunteerDO extends BaseEntity {

    /**
     * 志愿Id
     */
    private Long majorId;
    /**
     * 用户Id
     */
    private Long userId;
    /**
     * 学校Id
     */
    private Long schoolId;
    /**
     * 分数
     */
    private BigDecimal score;
    /**
     * 状态
     */
    private String status;
    public String getStatusName() {
        return DictCodeEnum.getValueById(DictCodeEnum.DICT_VOLUNTEER_STATUS.getCode(), this.status);
    }

    /**
     * 学校名称
     */
    @TableField(exist = false)
    private String schoolName;
    /**
     * 专业名称
     */
    @TableField(exist = false)
    private String majorName;
    /**
     * 用户名称
     */
    @TableField(exist = false)
    private String userName;

}
