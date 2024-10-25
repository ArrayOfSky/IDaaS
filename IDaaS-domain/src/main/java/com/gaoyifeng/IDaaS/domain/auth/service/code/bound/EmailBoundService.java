package com.gaoyifeng.IDaaS.domain.auth.service.code.bound;


import com.gaoyifeng.IDaaS.domain.auth.model.entity.UserAccountEntity;
import com.gaoyifeng.IDaaS.domain.auth.service.code.IVerifyMessageService;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;


@Service
public class EmailBoundService implements IVerifyMessageService {


    @Override
    public void verifyMessage(String flakeSnowId, String account) {
        UserAccountEntity userAccount = new UserAccountEntity();
        if(StringUtils.isEmpty(flakeSnowId)){
            userAccount.setFlakeSnowId(flakeSnowId);
        }else{
            userAccount.setFlakeSnowId(flakeSnowId);
        }
        userAccount.setEmail(account);
        userAccount.setCreateTime(new Date());
        userAccount.setUpdateTime(new Date());
        userAccount.setNickName("用户"+ RandomStringUtils.randomNumeric(6));

    }


}
