package com.home.examination.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.home.examination.entity.domain.NewsInformationDO;
import com.home.examination.mapper.NewsInformationMapper;
import com.home.examination.service.NewsInformationService;
import org.springframework.stereotype.Service;

@Service
public class NewsInformationServiceImpl extends ServiceImpl<NewsInformationMapper, NewsInformationDO> implements NewsInformationService {

}
