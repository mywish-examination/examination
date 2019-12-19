package com.home.examination.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.home.examination.entity.domain.NewsInformationDO;
import com.home.examination.entity.page.Pager;
import com.home.examination.mapper.NewsInformationMapper;
import com.home.examination.service.NewsInformationService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NewsInformationServiceImpl extends ServiceImpl<NewsInformationMapper, NewsInformationDO> implements NewsInformationService {

    @Override
    public Pager<NewsInformationDO> listPage(Pager<NewsInformationDO> pager) {
        List<NewsInformationDO> records = lambdaQuery().page(pager.getPager()).getRecords();

        pager.getPager().setTotal(records.size());
        return pager;
    }

}
