package com.practice.microservices.utility;

import lombok.Data;

@Data
public class GenericResponse <T>{

    private Integer status;

    private String msg;

    private String error;

    private T data;
}
