package com.gaoyifeng.IDaaS.domain.auth.repository;

public interface IUserAccountRepository {

    void putCacheCode(String account, String type, String code);
}
