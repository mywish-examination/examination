package com.home.examination.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.home.examination.entity.domain.MyCollectionDO;
import com.home.examination.mapper.MyCollectionMapper;
import com.home.examination.service.MyCollectionService;
import org.springframework.stereotype.Service;

@Service
public class MyCollectionServiceImpl extends ServiceImpl<MyCollectionMapper, MyCollectionDO> implements MyCollectionService {

}
