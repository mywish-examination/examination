package com.home.examination.entity.vo;

/**
 * @Description: 职业测评临时对象
 * @auth: fyb
 * @date: 2020/7/23 023
 */
public class HollandResultTemp {

    private Integer score;
    private String type;

    public HollandResultTemp(Integer score, String type) {
        this.score = score;
        this.type = type;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
