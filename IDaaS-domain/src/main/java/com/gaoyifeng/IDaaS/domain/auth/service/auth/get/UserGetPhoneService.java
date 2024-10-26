package com.gaoyifeng.IDaaS.domain.auth.service.auth.get;

import com.gaoyifeng.IDaaS.domain.auth.model.entity.UserAccountEntity;
import com.gaoyifeng.IDaaS.domain.auth.service.auth.IUserGetService;
import org.springframework.stereotype.Service;

@Service
public class UserGetPhoneService implements IUserGetService {
    @Override
    public UserAccountEntity getUserAccountByAccount(String account) {
        return null;
    }
}
