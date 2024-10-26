package com.gaoyifeng.IDaaS.domain.auth.service.auth.get;

import com.gaoyifeng.IDaaS.domain.auth.model.entity.UserAccountEntity;
import com.gaoyifeng.IDaaS.domain.auth.repository.IUserAccountRepository;
import com.gaoyifeng.IDaaS.domain.auth.service.auth.IUserGetService;
import com.gaoyifeng.IDaaS.domain.auth.service.code.factory.UserAccountFactory;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class UserGetEmailService implements IUserGetService {

    @Resource
    private IUserAccountRepository userAccountRepository;

    @Resource
    private UserAccountFactory userAccountFactory;

    @Override
    public UserAccountEntity getUserAccountByAccount(String account) {
        UserAccountEntity userAccount = userAccountRepository.selectUserByEmail(account);
        if(userAccount==null){
            userAccount = userAccountFactory.getDefaultUserAccount();
            userAccount.setEmail(account);
            userAccountRepository.insertUserAccount(userAccount);
        }
        return userAccount;
    }

}
