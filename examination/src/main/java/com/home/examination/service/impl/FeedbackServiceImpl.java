package com.home.examination.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.home.examination.entity.domain.FeedbackDO;
import com.home.examination.entity.domain.SchoolDO;
import com.home.examination.entity.page.Pager;
import com.home.examination.mapper.FeedbackMapper;
import com.home.examination.mapper.SchoolMapper;
import com.home.examination.service.FeedbackService;
import com.home.examination.service.SchoolService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class FeedbackServiceImpl extends ServiceImpl<FeedbackMapper, FeedbackDO> implements FeedbackService {

    @Override
    public Pager<FeedbackDO> listPage(Pager<FeedbackDO> pager) {
        List<FeedbackDO> records = lambdaQuery().page(pager.getPage()).getRecords();

        pager.getPage().setTotal(records.size());
        return pager;
    }

}
