package com.home.examination.service;

import com.home.examination.entity.domain.SchoolDO;
import com.home.examination.entity.domain.VolunteerDO;
import com.home.examination.entity.page.Pager;

public interface VolunteerService {

    Pager<VolunteerDO> listPage(Pager<VolunteerDO> pager);
}
