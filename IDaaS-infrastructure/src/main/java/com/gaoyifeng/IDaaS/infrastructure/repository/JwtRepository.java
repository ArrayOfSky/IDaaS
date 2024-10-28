package com.gaoyifeng.IDaaS.infrastructure.repository;

import com.gaoyifeng.IDaaS.domain.auth.repository.IJwtRepository;
import com.gaoyifeng.IDaaS.infrastructure.redis.IRedisService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Repository;

@Repository
public class JwtRepository implements IJwtRepository {

    @Resource
    private IRedisService redissonService;


    private final String REDIS_JWT = "jwt:";

    @Override
    public void put(String flakeSnowId, String jwt) {
        redissonService.setValue(REDIS_JWT+flakeSnowId, jwt,30*60*1000*60);
    }

    @Override
    public String get(String flakeSnowId) {
        return redissonService.getValue(REDIS_JWT+flakeSnowId);
    }

}
