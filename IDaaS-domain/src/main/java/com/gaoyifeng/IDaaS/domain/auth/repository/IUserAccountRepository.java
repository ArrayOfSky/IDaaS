package com.gaoyifeng.IDaaS.domain.auth.repository;

import com.gaoyifeng.IDaaS.domain.auth.model.entity.UserAccountEntity;

public interface IUserAccountRepository {

    void getCode(String account, String type,String code);

    void putCacheCode(String account, String type, String code);

    void insertUserAccount(UserAccountEntity user);

    void updateUserAccount(UserAccountEntity user);

    UserAccountEntity selectUserByEmail(String account);

    UserAccountEntity selectUserByPhone(String account);

    String getCacheCode(String account, String type);

    void deleteCacheCode(String account, String type);
}
