package com.home.examination.entity.vo;

import java.io.Serializable;

/**
 * holland_problem
 * @author 
 */
public class HollandProblemDTO implements Serializable {
    /**
     * 主键
     */
    private Integer id;

    /**
     * 题号
     */
    private Integer tiHao;

    /**
     * 问题内容
     */
    private String content;

    private Integer part;

    /**
     * 类型序号
     */
    private Integer typeNum;

    /**
     * 类型
     */
    private String type;

    /**
     * 分数
     */
    private Integer score;

    private static final long serialVersionUID = 1L;

    public Integer getTiHao() {
        return tiHao;
    }

    public void setTiHao(Integer tiHao) {
        this.tiHao = tiHao;
    }

    public Integer getTypeNum() {
        return typeNum;
    }

    public void setTypeNum(Integer typeNum) {
        this.typeNum = typeNum;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getPart() {
        return part;
    }

    public void setPart(Integer part) {
        this.part = part;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}