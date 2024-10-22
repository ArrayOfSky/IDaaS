package com.gaoyifeng.IDaaS.infrastructure.po;


import lombok.Data;

import java.util.Date;

@Data
public class UserAccountPo {
    private Long id;
    private String flakeSnowId;
    private String wxOpenId;
    private String wxUserName;
    private String nickName;
    private String icon;
    private String phone;
    private String email;
    private Date createTime;
    private Date updateTime;

}
