package com.gaoyifeng.IDaaS.infrastructure.repository;

import com.gaoyifeng.IDaaS.domain.auth.repository.IJwtRepository;
import com.gaoyifeng.IDaaS.infrastructure.redis.IRedisService;
import jakarta.annotation.Resource;

public class JwtRepository implements IJwtRepository {

    @Resource
    private IRedisService redissonService;

    @Override
    public void put(String flakeSnowId, String jwt) {
        redissonService.setValue(flakeSnowId, jwt);
    }

    @Override
    public String get(String flakeSnowId) {
        return redissonService.getValue(flakeSnowId);
    }

}
