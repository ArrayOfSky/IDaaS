package com.gaoyifeng.IDaaS.types.exception;


import lombok.Data;

@Data
public class BaseException extends RuntimeException {

    /**
     * 异常码
     */
    private String code;

    /**
     * 异常信息
     */
    private String message;


}

