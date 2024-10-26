package com.gaoyifeng.IDaaS.trigger.http.dto;


import lombok.Data;

@Data
public class AuthEntity {

    private String account;

    private String password;

    private String type;

}
