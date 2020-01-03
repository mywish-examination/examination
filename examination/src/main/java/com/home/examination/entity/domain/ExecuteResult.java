package com.home.examination.entity.domain;

import lombok.Data;

import java.io.Serializable;

@Data
public class ExecuteResult implements Serializable {

    private final static String SUCCESS = "success";
    private final static String ERROR = "error";

    private String status;
    private Object result;
    private String msg;

    public ExecuteResult() {
        this.status = this.SUCCESS;
    }

    public ExecuteResult(Object result) {
        this.result = result;
        this.status = this.SUCCESS;
    }

}
