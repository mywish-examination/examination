package com.home.examination.service;

import com.home.examination.entity.domain.NewsInformationDO;
import com.home.examination.entity.domain.SchoolDO;
import com.home.examination.entity.page.Pager;

public interface NewsInformationService {

    Pager<NewsInformationDO> listPage(Pager<NewsInformationDO> pager);
}
