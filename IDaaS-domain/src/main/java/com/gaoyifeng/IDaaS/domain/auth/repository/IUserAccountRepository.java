package com.gaoyifeng.IDaaS.domain.auth.repository;

import com.gaoyifeng.IDaaS.domain.auth.model.entity.UserAccountEntity;

public interface IUserAccountRepository {

    void getCode(String account, String type,String code);

    void putCacheCode(String account, String type, String code);

    UserAccountEntity selectUserByEmail(String account);

    UserAccountEntity selectUserByPhone(String account);

    String getCacheCode(String account, String type);
}
