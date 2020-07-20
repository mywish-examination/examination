package com.home.examination.entity.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("history_data_control_line")
public class HistoryDataControlLineDO extends BaseEntity {

    /**
     * 年份
     */
    private Integer years;
    /**
     * 文科-重点线
     */
    private Integer scienceKeyLine;
    /**
     * 文科-本科线
     */
    private Integer scienceUndergraduateLine;
    /**
     * 文科-专科线
     */
    private Integer scienceSpecialtyLine;
    /**
     * 理科-重点线
     */
    private Integer liberalArtsKeyLine;
    /**
     * 理科-本科线
     */
    private Integer liberalArtsUndergraduateLine;
    /**
     * 理科-专科线
     */
    private Integer liberalArtsSpecialtyLine;
}
