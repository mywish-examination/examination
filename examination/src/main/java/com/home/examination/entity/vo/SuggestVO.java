package com.home.examination.entity.vo;

import lombok.Data;

import java.util.List;

@Data
public class SuggestVO<T> {

    public SuggestVO(List<T> value) {
        this.code = "200";
        this.value = value;
    }

    private String message;
    private List<T> value;
    private String code;
    private String redirect;
}
