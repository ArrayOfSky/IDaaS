package com.gaoyifeng.IDaaS.domain.auth.repository;

import com.gaoyifeng.IDaaS.domain.auth.model.entity.UserFreshTokenEntity;

public interface IUserFreshTokenRepository {

    void save(String token,String userFlakeSnowId);

    UserFreshTokenEntity get(String userFlakeSnowId);


}
