package com.home.examination.service;

import com.home.examination.entity.domain.HollandProblemDO;
import com.home.examination.entity.domain.HollandResultDO;
import com.home.examination.entity.vo.HollandProblemDTO;

import java.util.List;

/**
 * @Description: 职业测评题目接口
 * @auth: fyb
 * @date: 2020/7/22 022
 */
public interface HollandProblemService {

    List<HollandProblemDO> getHollandProblemPage(Integer typeNum,Integer part);

    HollandResultDO getResult(List<HollandProblemDTO> hollandProblemDTOList);

}
