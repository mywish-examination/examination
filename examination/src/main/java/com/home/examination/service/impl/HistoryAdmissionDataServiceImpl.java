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
    public BigDecimal probabilityFilingHandler(List<HistoryAdmissionDataDO> historyAdmissionDataList, Integer userRank) {
        BigDecimal zero = new BigDecimal(0);

        BigDecimal size = new BigDecimal(historyAdmissionDataList.size());
        Integer collect = historyAdmissionDataList.stream().map(currHistoryAdmissionData -> {
            BigDecimal avgRankMultiply = new BigDecimal(currHistoryAdmissionData.getAvgRank()).multiply(new BigDecimal(0.33)).setScale(2, RoundingMode.HALF_UP);
            BigDecimal minRankMultiply = new BigDecimal(currHistoryAdmissionData.getMinimumRank()).multiply(new BigDecimal(0.66)).setScale(2, RoundingMode.HALF_UP);
            BigDecimal consultRank = minRankMultiply
                    .add(avgRankMultiply);

            if (new BigDecimal(userRank).compareTo(consultRank) <= 0) return 1;
            else return 0;
        }).reduce(0, (a, b) -> a + b);

        if (collect.intValue() == 0) return zero;
        BigDecimal result = new BigDecimal(collect).divide(size, 2, RoundingMode.HALF_UP);
        return result;
    }

    @Override
    public List<HistoryAdmissionDataDO> listHistoryAdmissionDataGroupYears(HistoryAdmissionDataDO historyAdmissionDataDO) {
        return historyAdmissionDataMapper.listHistoryAdmissionDataGroupYears(historyAdmissionDataDO);
    }

    @Override
    public List<HistoryAdmissionDataDO> listBaseDictData(String educationalCode, Long majorId, String userSubjectType) {
        return historyAdmissionDataMapper.listBaseDictData(educationalCode, majorId, userSubjectType);
    }

}
