package com.home.examination.entity.domain;

import java.io.Serializable;

/**
 * holland_problem
 * @author 
 */
public class HollandProblemDO implements Serializable {
    /**
     * 主键
     */
    private Integer id;

    /**
     * 题号
     */
    private Integer tihao;

    /**
     * 问题内容
     */
    private String content;

    private Integer part;

    /**
     * 类型序号
     */
    private Integer typenum;

    /**
     * 类型
     */
    private String type;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTihao() {
        return tihao;
    }

    public void setTihao(Integer tihao) {
        this.tihao = tihao;
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

    public Integer getTypenum() {
        return typenum;
    }

    public void setTypenum(Integer typenum) {
        this.typenum = typenum;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        HollandProblemDO other = (HollandProblemDO) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getTihao() == null ? other.getTihao() == null : this.getTihao().equals(other.getTihao()))
            && (this.getContent() == null ? other.getContent() == null : this.getContent().equals(other.getContent()))
            && (this.getPart() == null ? other.getPart() == null : this.getPart().equals(other.getPart()))
            && (this.getTypenum() == null ? other.getTypenum() == null : this.getTypenum().equals(other.getTypenum()))
            && (this.getType() == null ? other.getType() == null : this.getType().equals(other.getType()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getTihao() == null) ? 0 : getTihao().hashCode());
        result = prime * result + ((getContent() == null) ? 0 : getContent().hashCode());
        result = prime * result + ((getPart() == null) ? 0 : getPart().hashCode());
        result = prime * result + ((getTypenum() == null) ? 0 : getTypenum().hashCode());
        result = prime * result + ((getType() == null) ? 0 : getType().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", tihao=").append(tihao);
        sb.append(", content=").append(content);
        sb.append(", part=").append(part);
        sb.append(", typenum=").append(typenum);
        sb.append(", type=").append(type);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}