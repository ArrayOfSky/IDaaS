package com.gaoyifeng.IDaaS.domain.auth.service.code.factory;


import cn.hutool.core.util.IdUtil;
import com.gaoyifeng.IDaaS.domain.auth.model.entity.UserAccountEntity;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class UserAccountFactory {

    public UserAccountEntity getDefaultUserAccount(){
        Date updateTime = new Date();
        UserAccountEntity userAccount = new UserAccountEntity();
        userAccount.setUpdateTime(updateTime);
        userAccount.setCreateTime(updateTime);
        userAccount.setNickName("用户"+ RandomStringUtils.randomNumeric(6));
        userAccount.setFlakeSnowId(IdUtil.getSnowflakeNextIdStr());
        userAccount.setIcon("");
        return userAccount;
    }

}
