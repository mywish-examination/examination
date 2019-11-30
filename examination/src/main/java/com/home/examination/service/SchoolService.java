package com.home.examination.service;

import com.home.examination.entity.domain.SchoolDO;
import com.home.examination.entity.page.Pager;

public interface SchoolService {

    Pager<SchoolDO> listPage(Pager<SchoolDO> pager);
}
