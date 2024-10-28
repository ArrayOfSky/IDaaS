package com.gaoyifeng.IDaaS.domain.auth.repository;

public interface IJwtRepository {

    void put(String flakeSnowId, String jwt);

    String get(String flakeSnowId);
}
