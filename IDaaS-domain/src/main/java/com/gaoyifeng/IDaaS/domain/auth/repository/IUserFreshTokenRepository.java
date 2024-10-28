package com.gaoyifeng.IDaaS.domain.auth.repository;

import com.gaoyifeng.IDaaS.domain.auth.model.entity.UserFreshTokenEntity;

public interface IUserFreshTokenRepository {

    void save(String freshToken,String userFlakeSnowId,String token);

    UserFreshTokenEntity get(String userFlakeSnowId);

    void remove(String userFlakeSnowId);

    UserFreshTokenEntity getByToken(String refreshToken);

}
