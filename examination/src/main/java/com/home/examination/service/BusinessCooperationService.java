package com.home.examination.service;

import com.home.examination.entity.domain.BusinessCooperationDO;
import com.home.examination.entity.domain.SchoolDO;
import com.home.examination.entity.page.Pager;

public interface BusinessCooperationService {

    Pager<BusinessCooperationDO> listPage(Pager<BusinessCooperationDO> pager);
}
