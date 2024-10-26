package com.gaoyifeng.IDaaS.domain.auth.service.auth;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.BeanToMapCopier;
import cn.hutool.jwt.JWT;
import com.gaoyifeng.IDaaS.domain.auth.model.entity.UserAccountEntity;
import com.gaoyifeng.IDaaS.domain.auth.model.valobj.CodeTypeVo;
import com.gaoyifeng.IDaaS.domain.auth.repository.IUserAccountRepository;
import com.gaoyifeng.IDaaS.domain.auth.service.IAuthService;
import com.gaoyifeng.IDaaS.domain.auth.service.auth.get.UserGetEmailService;
import com.gaoyifeng.IDaaS.domain.auth.service.auth.get.UserGetPhoneService;
import com.gaoyifeng.IDaaS.domain.auth.service.auth.jwt.JwtUtils;
import com.gaoyifeng.IDaaS.types.commom.Constants;
import com.gaoyifeng.IDaaS.types.exception.BaseException;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class AuthService implements IAuthService {

    @Resource
    private IUserAccountRepository userAccountRepository;

    @Resource
    private JwtUtils jwtUtils;

    private Map<CodeTypeVo,IUserGetService> userGetServiceMap = new HashMap(2);

    public AuthService(UserGetEmailService userGetEmailService,
                       UserGetPhoneService userGetPhoneService) {
        userGetServiceMap.put(CodeTypeVo.LOGIN_EMAIL, userGetEmailService);
        userGetServiceMap.put(CodeTypeVo.LOGIN_PHONE, userGetPhoneService);
    }


    @Override
    public String login(String account, String password,String type) {
        // 验证账号密码是否正确
        log.info("验证账号密码是否正确");
        String cacheCode = userAccountRepository.getCacheCode(account, type);
        if(!cacheCode.equals(password)){
            throw new BaseException(Constants.ResponseCode.ILLEGAL_PARAMETER,"验证码错误");
        }

        // 获取账号信息
        log.info("获取账号信息");
        UserAccountEntity userAccount = userGetServiceMap.get(CodeTypeVo.valueOf(type)).getUserAccountByAccount(account);

        // 生产令牌
        log.info("生产令牌");
        String token = jwtUtils.encode(userAccount.getFlakeSnowId(), 24 * 60 * 60 * 1000, BeanUtil.beanToMap(userAccount));

        return token;
    }

    @Override
    public Map verify(String token) {
        return jwtUtils.decode(token);
    }

}
