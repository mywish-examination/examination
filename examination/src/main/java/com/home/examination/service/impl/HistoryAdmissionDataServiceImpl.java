package com.home.examination.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.home.examination.entity.domain.HistoryAdmissionDataDO;
import com.home.examination.entity.domain.RankParagraphDO;
import com.home.examination.entity.domain.UserDO;
import com.home.examination.entity.vo.AdmissionEstimateReferenceDO;
import com.home.examination.mapper.HistoryAdmissionDataMapper;
import com.home.examination.service.HistoryAdmissionDataService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class HistoryAdmissionDataServiceImpl extends ServiceImpl<HistoryAdmissionDataMapper, HistoryAdmissionDataDO> implements HistoryAdmissionDataService {

    @Resource
    private HistoryAdmissionDataMapper historyAdmissionDataMapper;
    @Resource
    private RedisTemplate redisTemplate;

    @Override
    public List<HistoryAdmissionDataDO> pageByQueryWrapper(Wrapper<HistoryAdmissionDataDO> queryWrapper) {
        return historyAdmissionDataMapper.pageByQueryWrapper(queryWrapper);
    }

    @Override
    public int countByQueryWrapper(Wrapper<HistoryAdmissionDataDO> queryWrapper) {
        return historyAdmissionDataMapper.countByQueryWrapper(queryWrapper);
    }

    @Override
    public RankParagraphDO getRankParagraphBySchool(String educationalCode, String year) {
        return historyAdmissionDataMapper.getRankParagraphBySchool(educationalCode, year);
    }

    @Override
    public AdmissionEstimateReferenceDO getBySchoolOrMajor(Wrapper<HistoryAdmissionDataDO> queryWrapper) {
        return historyAdmissionDataMapper.getBySchoolOrMajor(queryWrapper);
    }

    @Override
    public BigDecimal probabilityFilingHandler(List<HistoryAdmissionDataDO> historyAdmissionDataList, UserDO userDO) {
        int year = LocalDate.now().minusYears(3).getYear();

        BigDecimal zero = new BigDecimal(0);
        List<BigDecimal> collect = historyAdmissionDataList.stream()
                .filter(historyAdmissionDataDO -> Integer.valueOf(historyAdmissionDataDO.getYears()) > year).map(currHistoryAdmissionData -> {
                    BigDecimal consultRank = (new BigDecimal(currHistoryAdmissionData.getMinimumRank()).multiply(new BigDecimal(0.66)))
                            .add(new BigDecimal(currHistoryAdmissionData.getAvgRank()).multiply(new BigDecimal(0.33)));
                    String rank = userDO.getRank();
                    if (new BigDecimal(rank).compareTo(consultRank) <= 0) return new BigDecimal(1);
                    else return new BigDecimal(0);
                }).collect(Collectors.toList());

        if(collect.isEmpty()) return zero;

        BigDecimal result = new BigDecimal(collect.size()).divide(collect.stream().reduce(zero, (a, b) -> a.add(b)), 2, RoundingMode.HALF_UP);

        return result;
    }

}
