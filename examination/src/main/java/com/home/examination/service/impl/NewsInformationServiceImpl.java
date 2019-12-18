package com.home.examination.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.home.examination.entity.domain.NewsInformationDO;
import com.home.examination.entity.domain.SchoolDO;
import com.home.examination.entity.page.Pager;
import com.home.examination.mapper.NewsInformationMapper;
import com.home.examination.mapper.SchoolMapper;
import com.home.examination.service.NewsInformationService;
import com.home.examination.service.SchoolService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class NewsInformationServiceImpl extends ServiceImpl<NewsInformationMapper, NewsInformationDO> implements NewsInformationService {

    @Override
    public Pager<NewsInformationDO> listPage(Pager<NewsInformationDO> pager) {
        List<NewsInformationDO> records = lambdaQuery().page(pager.getPage()).getRecords();

        pager.getPage().setTotal(records.size());
        return pager;
    }

}
