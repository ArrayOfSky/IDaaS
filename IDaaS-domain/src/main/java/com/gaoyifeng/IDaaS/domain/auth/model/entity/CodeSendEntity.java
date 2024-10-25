package com.gaoyifeng.IDaaS.domain.auth.model.entity;


import lombok.Data;

@Data
public class CodeSendEntity {

    private String account;
    private String type;
    private String code;


}
