package com.gaoyifeng.IDaaS.infrastructure.po;

import lombok.Data;

import java.util.Date;

@Data
public class UserFreshTokenPo {

    private Long id;
    private String freshToken;

    private String userFlakeSnowId;

    private Date createTime;

}
