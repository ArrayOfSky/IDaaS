package com.gaoyifeng.IDaaS.domain.auth.service.code.bound;


import cn.hutool.core.util.IdUtil;
import com.gaoyifeng.IDaaS.domain.auth.model.entity.UserAccountEntity;
import com.gaoyifeng.IDaaS.domain.auth.repository.IUserAccountRepository;
import com.gaoyifeng.IDaaS.domain.auth.service.code.IVerifyMessageService;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.IdGenerator;
import org.springframework.util.StringUtils;

import java.util.Date;


@Service
public class EmailBoundService implements IVerifyMessageService {

    @Resource
    private IUserAccountRepository userAccountRepository;

    @Override
    public void verifyMessage(String flakeSnowId, String account) {
        Date updateTime = new Date();
        UserAccountEntity userAccount = new UserAccountEntity();
        userAccount.setEmail(account);
        userAccount.setUpdateTime(updateTime);
        if(StringUtils.isEmpty(flakeSnowId)){
            flakeSnowId = IdUtil.getSnowflakeNextIdStr();
            //注册
            userAccount.setNickName("用户"+ RandomStringUtils.randomNumeric(6));
            userAccount.setFlakeSnowId(flakeSnowId);
            userAccount.setCreateTime(updateTime);
            userAccountRepository.insertUserAccount(userAccount);
        }else{
            //绑定
            userAccount.setFlakeSnowId(flakeSnowId);
            userAccountRepository.updateUserAccount(userAccount);
        }
    }


}
