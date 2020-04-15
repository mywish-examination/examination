package com.home.examination.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.home.examination.entity.domain.SchoolDO;
import com.home.examination.entity.vo.ByNameVO;

import java.util.List;

public interface SchoolService extends IService<SchoolDO> {

    SchoolDO getByEducationalCode(String educationalCode);

    List<ByNameVO> listByName(Wrapper<SchoolDO> queryWrapper);

    List<SchoolDO> listSchool(Long userId, Long majorId);

}
