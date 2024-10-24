package com.gaoyifeng.IDaaS.domain.auth.repository;

import com.gaoyifeng.IDaaS.domain.auth.model.UserAccountEntity;

public interface IUserAccountRepository {

    void putCacheCode(String account, String type, String code);

    UserAccountEntity selectUserByEmail(String account);

    UserAccountEntity selectUserByPhone(String account);
}
