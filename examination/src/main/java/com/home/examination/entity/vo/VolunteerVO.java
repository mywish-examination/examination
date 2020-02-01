package com.home.examination.entity.vo;

import com.home.examination.entity.domain.HistoryAdmissionDataDO;
import com.home.examination.entity.domain.MajorDO;
import com.home.examination.entity.domain.SchoolDO;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class VolunteerVO {

    /**
     * 学校列表
     */
    private List<SchoolDO> schoolList;

}
