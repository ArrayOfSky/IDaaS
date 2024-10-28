package com.gaoyifeng.IDaaS.domain.auth.model.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserFreshTokenEntity {

    private String freshToken;


    private String userFlakeSnowId;

    private Date createTime;

    private String token;

}
