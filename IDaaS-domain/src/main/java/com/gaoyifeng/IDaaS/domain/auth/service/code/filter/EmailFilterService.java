package com.gaoyifeng.IDaaS.domain.auth.service.code.filter;

import com.gaoyifeng.IDaaS.domain.auth.model.entity.UserAccountEntity;
import com.gaoyifeng.IDaaS.domain.auth.repository.IUserAccountRepository;
import com.gaoyifeng.IDaaS.domain.auth.service.code.ISendFilterService;
import com.gaoyifeng.IDaaS.types.commom.Constants;
import com.gaoyifeng.IDaaS.types.exception.BaseException;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class EmailFilterService implements ISendFilterService {

    @Resource
    private IUserAccountRepository userAccountRepositor;

    @Override
    public void doFilter(String account) {
        log.info("检查是否已绑定邮箱");
        UserAccountEntity userAccount = userAccountRepositor.selectUserByEmail(account);
        if(userAccount!=null){
            throw new BaseException(Constants.ResponseCode.ILLEGAL_PARAMETER, "邮箱已绑定账号");
        }
    }
}
