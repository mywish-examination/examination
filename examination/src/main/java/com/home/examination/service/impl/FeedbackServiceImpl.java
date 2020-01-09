package com.home.examination.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.home.examination.entity.domain.FeedbackDO;
import com.home.examination.mapper.FeedbackMapper;
import com.home.examination.service.FeedbackService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class FeedbackServiceImpl extends ServiceImpl<FeedbackMapper, FeedbackDO> implements FeedbackService {

    @Resource
    private FeedbackMapper feedbackMapper;

    @Override
    public List<FeedbackDO> pager(Wrapper<FeedbackDO> queryWrapper) {
        return feedbackMapper.pageByQueryWrapper(queryWrapper);
    }

    @Override
    public int count(Wrapper<FeedbackDO> queryWrapper) {
        return feedbackMapper.countByQueryWrapper(queryWrapper);
    }

}
