package com.home.examination.controller.app;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.home.examination.entity.domain.HistoryAdmissionDataDO;
import com.home.examination.entity.vo.ExecuteResult;
import com.home.examination.entity.vo.HollandProblemDTO;
import com.home.examination.service.HollandProblemService;
import org.apache.ibatis.annotations.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Collection;
import java.util.List;

/**
 * @Description: 职业测评相关
 * @auth: fyb
 * @date: 2020/7/22 022
 */
@Controller
public class HollandController {

    private static final Logger logger = LoggerFactory.getLogger(HollandController.class);

    @Resource
    private HollandProblemService hollandProblemService;


    @GetMapping("/app/profession/list")
    @ResponseBody
    public ExecuteResult list(@Param("typeNum") Integer typeNum, @Param("part") Integer part) {

        if (StringUtils.isEmpty(part)){
            return new ExecuteResult(false,"题目部分数目不能为空");
        }
        if (StringUtils.isEmpty(typeNum)){
            return new ExecuteResult(false,"题目类型不能为空");
        }

        if (typeNum>6 || typeNum<1){
            return new ExecuteResult(false,"题目类型参数不合法");
        }
        if (part>4 || part<1){
            return new ExecuteResult(false,"题目部分数目参数不合法");
        }

        return new ExecuteResult<>(hollandProblemService.getHollandProblemPage(typeNum,part));
    }


    @PostMapping("/app/profession/getResult")
    @ResponseBody
    public ExecuteResult list(@RequestBody HollandProblemDTO hollandProblemDTOList) {

//        List<HollandProblemDTO> hollandProblemDTOList = null;
//        try {
//            hollandProblemDTOList = new ObjectMapper().readValue(jsonAsString, new TypeReference<Collection<HollandProblemDTO>>() { });
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        return new ExecuteResult<>(hollandProblemService.getResult(hollandProblemDTOList.getList()));
    }

}
