package com.gaoyifeng.IDaaS.domain.auth.service.code.filter;

import com.gaoyifeng.IDaaS.domain.auth.model.UserAccountEntity;
import com.gaoyifeng.IDaaS.domain.auth.repository.IUserAccountRepository;
import com.gaoyifeng.IDaaS.domain.auth.service.code.ISendFilterService;
import com.gaoyifeng.IDaaS.types.commom.Constants;
import com.gaoyifeng.IDaaS.types.exception.BaseException;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;


@Service
public class PhoneFilterService implements ISendFilterService {

    @Resource
    private IUserAccountRepository userAccountRepositor;

    @Override
    public void doFilter(String account) {
        UserAccountEntity userAccount = userAccountRepositor.selectUserByPhone(account);
        if(userAccount!=null){
            throw new BaseException(Constants.ResponseCode.ILLEGAL_PARAMETER, "手机号已绑定账号");
        }
    }
}
