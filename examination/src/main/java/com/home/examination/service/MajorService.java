package com.home.examination.service;

import com.home.examination.entity.domain.MajorDO;
import com.home.examination.entity.domain.SchoolDO;
import com.home.examination.entity.page.Pager;

public interface MajorService {

    Pager<MajorDO> listPage(Pager<MajorDO> pager);
}
