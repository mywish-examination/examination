package com.home.examination.entity.domain;

import java.io.Serializable;

/**
 * holland_result
 * @author 
 */
public class HollandResultDO implements Serializable {
    private Integer id;

    private String code;

    private String typicalMajor;

    private String typicalProfession;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTypicalMajor() {
        return typicalMajor;
    }

    public void setTypicalMajor(String typicalMajor) {
        this.typicalMajor = typicalMajor;
    }

    public String getTypicalProfession() {
        return typicalProfession;
    }

    public void setTypicalProfession(String typicalProfession) {
        this.typicalProfession = typicalProfession;
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
        HollandResultDO other = (HollandResultDO) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getCode() == null ? other.getCode() == null : this.getCode().equals(other.getCode()))
            && (this.getTypicalMajor() == null ? other.getTypicalMajor() == null : this.getTypicalMajor().equals(other.getTypicalMajor()))
            && (this.getTypicalProfession() == null ? other.getTypicalProfession() == null : this.getTypicalProfession().equals(other.getTypicalProfession()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getCode() == null) ? 0 : getCode().hashCode());
        result = prime * result + ((getTypicalMajor() == null) ? 0 : getTypicalMajor().hashCode());
        result = prime * result + ((getTypicalProfession() == null) ? 0 : getTypicalProfession().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", code=").append(code);
        sb.append(", typicalMajor=").append(typicalMajor);
        sb.append(", typicalProfession=").append(typicalProfession);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}