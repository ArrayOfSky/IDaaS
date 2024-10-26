package com.gaoyifeng.IDaaS.domain.auth.service.code.bound;


import cn.hutool.core.util.IdUtil;
import com.gaoyifeng.IDaaS.domain.auth.model.entity.UserAccountEntity;
import com.gaoyifeng.IDaaS.domain.auth.repository.IUserAccountRepository;
import com.gaoyifeng.IDaaS.domain.auth.service.code.IVerifyMessageService;
import com.gaoyifeng.IDaaS.domain.auth.service.code.factory.UserAccountFactory;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.IdGenerator;
import org.springframework.util.StringUtils;

import java.util.Date;


@Service
@Slf4j
public class EmailBoundService implements IVerifyMessageService {

    @Resource
    private IUserAccountRepository userAccountRepository;

    @Resource
    private UserAccountFactory userAccountFactory;

    @Override
    public void verifyMessage(String flakeSnowId, String account) {
        UserAccountEntity userAccount = userAccountFactory.getDefaultUserAccount();
        userAccount.setEmail(account);
        if(StringUtils.isEmpty(flakeSnowId)){
            //注册
            log.info("用户注册");
            userAccountRepository.insertUserAccount(userAccount);
        }else{
            //绑定
            log.info("绑定邮箱");
            userAccount.setFlakeSnowId(flakeSnowId);
            userAccountRepository.updateUserAccount(userAccount);
        }
    }


}
