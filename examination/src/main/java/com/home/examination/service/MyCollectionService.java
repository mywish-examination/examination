package com.home.examination.service;

import com.home.examination.entity.domain.MyCollectionDO;
import com.home.examination.entity.domain.SchoolDO;
import com.home.examination.entity.page.Pager;

public interface MyCollectionService {

    Pager<MyCollectionDO> listPage(Pager<MyCollectionDO> pager);
}
