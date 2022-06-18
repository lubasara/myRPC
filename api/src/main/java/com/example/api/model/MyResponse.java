package com.example.api.model;

import lombok.Data;

@Data
public class MyResponse {

    private String msg;
    private Integer code;
    private Object data;
}
