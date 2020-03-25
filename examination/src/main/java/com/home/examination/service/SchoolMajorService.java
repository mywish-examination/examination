package com.home.examination.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.home.examination.entity.domain.SchoolMajorDO;
import com.home.examination.entity.vo.ByNameVO;

import java.util.List;

public interface SchoolMajorService extends IService<SchoolMajorDO> {

    List<SchoolMajorDO> pageByQueryWrapper(Wrapper<SchoolMajorDO> queryWrapper);

    int countByQueryWrapper(Wrapper<SchoolMajorDO> queryWrapper);

    List<ByNameVO> listByName(Wrapper<SchoolMajorDO> queryWrapper);

}
