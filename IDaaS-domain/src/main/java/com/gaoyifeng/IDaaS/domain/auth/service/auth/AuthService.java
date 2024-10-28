package com.gaoyifeng.IDaaS.domain.auth.service.auth;

import cn.hutool.core.bean.BeanUtil;
import com.gaoyifeng.IDaaS.domain.auth.model.entity.UserAccountEntity;
import com.gaoyifeng.IDaaS.domain.auth.model.entity.UserFreshTokenEntity;
import com.gaoyifeng.IDaaS.domain.auth.model.valobj.CodeTypeVo;
import com.gaoyifeng.IDaaS.domain.auth.repository.IUserAccountRepository;
import com.gaoyifeng.IDaaS.domain.auth.repository.IUserFreshTokenRepository;
import com.gaoyifeng.IDaaS.domain.auth.service.IAuthService;
import com.gaoyifeng.IDaaS.domain.auth.service.auth.get.UserGetEmailService;
import com.gaoyifeng.IDaaS.domain.auth.service.auth.get.UserGetPhoneService;
import com.gaoyifeng.IDaaS.domain.auth.service.auth.jwt.JwtUtils;
import com.gaoyifeng.IDaaS.types.commom.Constants;
import com.gaoyifeng.IDaaS.types.exception.BaseException;
import com.gaoyifeng.IDaaS.types.utils.HttpThreadLocalUtil;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class AuthService implements IAuthService {

    @Resource
    private IUserAccountRepository userAccountRepository;

    @Resource
    private IUserFreshTokenRepository userFreshTokenRepository;

    @Resource
    private JwtUtils jwtUtils;

    private Map<CodeTypeVo, IUserGetService> userGetServiceMap = new HashMap(2);

    public AuthService(UserGetEmailService userGetEmailService,
                       UserGetPhoneService userGetPhoneService) {
        userGetServiceMap.put(CodeTypeVo.LOGIN_EMAIL, userGetEmailService);
        userGetServiceMap.put(CodeTypeVo.LOGIN_PHONE, userGetPhoneService);
    }


    @Override
    public void login(String account, String password, String type) {
        // 验证账号密码是否正确
        log.info("验证账号密码是否正确");
        String cacheCode = userAccountRepository.getCacheCode(account, type);
        if (cacheCode==null||!cacheCode.equals(password)) {
            throw new BaseException(Constants.ResponseCode.ILLEGAL_PARAMETER, "验证码错误");
        }

        // 获取账号信息
        log.info("获取账号信息");
        UserAccountEntity userAccount = userGetServiceMap.get(CodeTypeVo.getCodeType(type)).getUserAccountByAccount(account);

        userAccountRepository.deleteCacheCode(account, type);

        // 生产令牌
        log.info("生产令牌");
        String token = jwtUtils.encode(userAccount.getFlakeSnowId(), BeanUtil.beanToMap(userAccount));
        HttpThreadLocalUtil.getResponse().addHeader("Authorization", token);

        // 生成refresh-token
        userFreshTokenRepository.remove(userAccount.getFlakeSnowId());
        String freshToken = RandomStringUtils.randomNumeric(16) + "idaas";
        userFreshTokenRepository.save(freshToken,userAccount.getFlakeSnowId(),token);
        HttpThreadLocalUtil.getResponse().addHeader("refresh-token", freshToken);
    }

    @Override
    public Map<String,String> verify(String token) {
        if(jwtUtils.isVerify(token)){
            return jwtUtils.decode(token);
        }
        throw new BaseException(Constants.ResponseCode.ILLEGAL_PARAMETER, "token非法");
    }

    @Override
    public void renewval(String token, String refreshToken) {
        UserFreshTokenEntity userFreshTokenEntity = userFreshTokenRepository.getByToken(refreshToken);
        if(userFreshTokenEntity!=null&&userFreshTokenEntity.getToken().equals(token)){
            String flakeSnowId = userFreshTokenEntity.getUserFlakeSnowId();
            UserAccountEntity userAccount = userAccountRepository.selectUserByFlakeSnowId(flakeSnowId);
            // 生产令牌
            log.info("生产令牌");
            String jwt = jwtUtils.encode(flakeSnowId,  BeanUtil.beanToMap(userAccount));
            HttpThreadLocalUtil.getResponse().addHeader("Authorization", jwt);

            // 生成refresh-token
            userFreshTokenRepository.remove(flakeSnowId);
            String freshToken = RandomStringUtils.randomNumeric(16);
            userFreshTokenRepository.save(freshToken,userAccount.getFlakeSnowId(),jwt);
            HttpThreadLocalUtil.getResponse().addHeader("refresh-token", freshToken);
        }else{
            throw new BaseException(Constants.ResponseCode.ILLEGAL_PARAMETER, "refreshToken非法");
        }
    }

}
