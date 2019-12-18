package com.home.examination.service;

import com.home.examination.entity.domain.AreaDO;
import com.home.examination.entity.domain.SchoolDO;
import com.home.examination.entity.page.Pager;

public interface AreaService {

    Pager<AreaDO> listPage(Pager<AreaDO> pager);
}
