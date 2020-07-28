package com.home.examination.entity.vo;

import java.io.Serializable;
import java.util.List;

/**
 * holland_problem
 * @author 
 */
public class HollandProblemDTO implements Serializable {

    private List<HollandProblemWithScore> list;

    public List<HollandProblemWithScore> getList() {
        return list;
    }

    public void setList(List<HollandProblemWithScore> list) {
        this.list = list;
    }
}