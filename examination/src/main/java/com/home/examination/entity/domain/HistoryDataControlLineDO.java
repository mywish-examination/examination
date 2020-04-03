package com.home.examination.entity.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("history_data_control_line")
public class HistoryDataControlLineDO extends BaseEntity {

    /**
     * 年份
     */
    private String years;
    /**
     * 文科-重点线
     */
    private String scienceKeyLine;
    /**
     * 文科-本科线
     */
    private String scienceUndergraduateLine;
    /**
     * 文科-专科线
     */
    private String scienceSpecialtyLine;
    /**
     * 理科-重点线
     */
    private String liberalArtsKeyLine;
    /**
     * 理科-本科线
     */
    private String liberalArtsUndergraduateLine;
    /**
     * 理科-专科线
     */
    private String liberalArtsSpecialtyLine;
}
