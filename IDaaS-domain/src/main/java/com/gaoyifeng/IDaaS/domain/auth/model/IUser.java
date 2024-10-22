package com.gaoyifeng.IDaaS.domain.auth.model;


import lombok.Data;

@Data
public class IUser {

    private Long id;
    private String wxOpenId;
    private String wxUserName;
    private String nickName;
    private String icon;
    private String phone;
    private String email;

}
