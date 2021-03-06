package com.home.examination.entity.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class ExecuteResult<T> implements Serializable {

    private final static String SUCCESS = "success";
    private final static String ERROR = "error";

    private String status;
    private T result;
    private String msg;

    public ExecuteResult() {
        this.status = this.SUCCESS;
    }

    public ExecuteResult(T result) {
        this.result = result;
        this.status = this.SUCCESS;
    }

    public ExecuteResult(boolean result) {
        this.status = result ? this.SUCCESS : this.ERROR;
        if(result == false) this.msg = "系统异常";
        if(result == true) this.msg = "success";
    }

    public ExecuteResult(boolean result, String msg) {
        this.status = result ? this.SUCCESS : this.ERROR;
        this.msg = msg;
    }

    public void setData(T result) {
        this.result = result;
    }

}
