package com.home.examination.service;

import com.home.examination.entity.domain.FeedbackDO;
import com.home.examination.entity.domain.SchoolDO;
import com.home.examination.entity.page.Pager;

public interface FeedbackService {

    Pager<FeedbackDO> listPage(Pager<FeedbackDO> pager);
}
