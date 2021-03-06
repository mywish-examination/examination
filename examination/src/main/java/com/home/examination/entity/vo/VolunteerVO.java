package com.home.examination.entity.vo;

import com.home.examination.entity.domain.SchoolDO;
import lombok.Data;

import java.util.List;

@Data
public class VolunteerVO {

    /**
     * 学校列表
     */
    private List<SchoolDO> schoolList;

}
