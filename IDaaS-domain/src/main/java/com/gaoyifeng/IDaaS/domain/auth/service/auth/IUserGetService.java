package com.gaoyifeng.IDaaS.domain.auth.service.auth;

import com.gaoyifeng.IDaaS.domain.auth.model.entity.UserAccountEntity;

public interface IUserGetService {

    UserAccountEntity getUserAccountByAccount(String account);

}
