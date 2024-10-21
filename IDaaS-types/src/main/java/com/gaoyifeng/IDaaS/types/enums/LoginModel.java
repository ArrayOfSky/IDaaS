package com.gaoyifeng.IDaaS.types.enums;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum LoginModel {

    PASSWORD("0"),
    EMAIL("1"),
    PHONE("2"),
    WX("3");

    private final String code;

}
