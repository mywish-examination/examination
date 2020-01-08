package com.home.examination.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.home.examination.entity.domain.MajorDO;
import com.home.examination.entity.domain.SchoolMajorDO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SchoolMajorService extends IService<SchoolMajorDO> {

    List<MajorDO> listMajorBySchoolId(Long schoolId);

}
