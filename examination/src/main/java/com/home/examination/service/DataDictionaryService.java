package com.home.examination.service;

import com.home.examination.entity.domain.DataDictionaryDO;
import com.home.examination.entity.domain.SchoolDO;
import com.home.examination.entity.page.Pager;

public interface DataDictionaryService {

    Pager<DataDictionaryDO> listPage(Pager<DataDictionaryDO> pager);
}
