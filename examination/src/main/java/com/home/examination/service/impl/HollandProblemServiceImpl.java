package com.home.examination.service.impl;

import com.home.examination.common.enumerate.HollandEnum;
import com.home.examination.entity.domain.HollandProblemDO;
import com.home.examination.entity.domain.HollandResultDO;
import com.home.examination.entity.vo.ExecuteResult;
import com.home.examination.entity.vo.HollandProblemDTO;
import com.home.examination.entity.vo.HollandResultTemp;
import com.home.examination.mapper.HollandProblemMapper;
import com.home.examination.mapper.HollandResultMapper;
import com.home.examination.service.HollandProblemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * @Description: 职业测评问题service
 * @auth: fyb
 * @date: 2020/7/22 022
 */
@Service
public class HollandProblemServiceImpl implements HollandProblemService {

    private static final Logger logger = LoggerFactory.getLogger(HollandProblemServiceImpl.class);

    //根据六边形图，可得到每种类型间距
    //邻=1  间=2  对=3
    private static final Map<String,Integer> typeDistanceMap = new HashMap<>();
    static {
        //R相关的
        typeDistanceMap.put("RI",1);
        typeDistanceMap.put("IR",1);
        typeDistanceMap.put("RA",2);
        typeDistanceMap.put("AR",2);
        typeDistanceMap.put("RS",3);
        typeDistanceMap.put("SR",3);
        typeDistanceMap.put("RE",2);
        typeDistanceMap.put("ER",2);
        typeDistanceMap.put("RC",1);
        typeDistanceMap.put("CR",1);
        //I相关
        typeDistanceMap.put("IA",1);
        typeDistanceMap.put("AI",1);
        typeDistanceMap.put("IS",2);
        typeDistanceMap.put("SI",2);
        typeDistanceMap.put("IE",3);
        typeDistanceMap.put("EI",3);
        typeDistanceMap.put("IC",2);
        typeDistanceMap.put("CI",2);
        //A相关
        typeDistanceMap.put("AS",1);
        typeDistanceMap.put("SA",1);
        typeDistanceMap.put("AE",2);
        typeDistanceMap.put("EA",2);
        typeDistanceMap.put("AC",3);
        typeDistanceMap.put("CA",3);
        //S相关
        typeDistanceMap.put("SE",1);
        typeDistanceMap.put("ES",1);
        typeDistanceMap.put("SC",2);
        typeDistanceMap.put("CS",2);
        //E相关
        typeDistanceMap.put("EC",1);
        typeDistanceMap.put("CE",1);
    }

    @Resource
    private HollandProblemMapper hollandProblemMapper;
    @Resource
    private HollandResultMapper hollandResultMapper;

    @Override
    public List<HollandProblemDO> getHollandProblemPage(Integer typeNum,Integer part) {

        List<HollandProblemDO> hollandProblemDOS = hollandProblemMapper.selectByTypeNumAndPart(typeNum,part);

        return hollandProblemDOS;
    }

    @Override
    public HollandResultDO getResult(List<HollandProblemDTO> hollandProblemDTOList) {

        //todo 第五部分的分数  也需要传过来

        //根据类型把各自分数计算出来，取分数最高的三个
        //分数相同时，取高分相邻的，
        //假如：RIAS，其中AS得分一样，则取A，不取S，因为A与I相邻。如果都相邻则取与最高得分相更近的
        // 但是   RIASEC  是循环的圈

        //返回最高分数组合类型的测试结果

        int scoreR = 0;
        int scoreI = 0;
        int scoreA = 0;
        int scoreS = 0;
        int scoreE = 0;
        int scoreC = 0;

        for (HollandProblemDTO hollandProblemDTO : hollandProblemDTOList){
            if(HollandEnum.R.getCode().equals(hollandProblemDTO.getType())){
                scoreR = scoreR + hollandProblemDTO.getScore();
            }
            if(HollandEnum.I.getCode().equals(hollandProblemDTO.getType())){
                scoreI = scoreI+ hollandProblemDTO.getScore();
            }
            if(HollandEnum.A.getCode().equals(hollandProblemDTO.getType())){
                scoreA = scoreA+ hollandProblemDTO.getScore();
            }
            if(HollandEnum.S.getCode().equals(hollandProblemDTO.getType())){
                scoreS = scoreS+ hollandProblemDTO.getScore();
            }
            if(HollandEnum.E.getCode().equals(hollandProblemDTO.getType())){
                scoreE = scoreE+ hollandProblemDTO.getScore();
            }
            if(HollandEnum.C.getCode().equals(hollandProblemDTO.getType())){
                scoreC = scoreC+ hollandProblemDTO.getScore();
            }
        }

        List<HollandResultTemp> scoreList = new ArrayList<>();
        scoreList.add(new HollandResultTemp(scoreR,HollandEnum.R.getCode()));
        scoreList.add(new HollandResultTemp(scoreI,HollandEnum.I.getCode()));
        scoreList.add(new HollandResultTemp(scoreA,HollandEnum.A.getCode()));
        scoreList.add(new HollandResultTemp(scoreS,HollandEnum.S.getCode()));
        scoreList.add(new HollandResultTemp(scoreE,HollandEnum.E.getCode()));
        scoreList.add(new HollandResultTemp(scoreC,HollandEnum.C.getCode()));

        scoreList.sort(new Comparator<HollandResultTemp>() {
            @Override
            public int compare(HollandResultTemp o1, HollandResultTemp o2) {
                return o1.getScore()-o2.getScore();
            }
        });

        String firstScoreKey = scoreList.get(scoreList.size()-1).getType();

        //todo  时间来不及  先不加逻辑
        //判断分数相同的情况
//        HollandResultTemp temp1 = scoreList.get(scoreList.size()-1);
//        HollandResultTemp temp2 = scoreList.get(scoreList.size()-2);
//        HollandResultTemp temp3 = scoreList.get(scoreList.size()-3);
//        HollandResultTemp temp4 = scoreList.get(scoreList.size()-4);
//        HollandResultTemp temp5 = scoreList.get(scoreList.size()-5);
//        HollandResultTemp temp6 = scoreList.get(scoreList.size()-6);
//
//        if (temp2.getScore() == temp3.getScore()){
//            if (temp2.getScore() == temp4.getScore()){
//
//            }
//        }


        String secondScoreKey = scoreList.get(scoreList.size()-2).getType();
        String thirdScoreKey = scoreList.get(scoreList.size()-3).getType();

        String resultKey = firstScoreKey+secondScoreKey+thirdScoreKey;

        HollandResultDO hollandResultDO = hollandResultMapper.selectByCode(resultKey);

        return hollandResultDO;
    }

    /**
     * 获取对应value的key
     * @param map
     * @return
     */
    private String getKey(Map<String,Integer> map, int value){
        String resultKey = null;
        for(Map.Entry<String,Integer> str : map.entrySet()){

            if(value == str.getValue()){
                resultKey = str.getKey();
            }

        }
        return resultKey;
    }




}
