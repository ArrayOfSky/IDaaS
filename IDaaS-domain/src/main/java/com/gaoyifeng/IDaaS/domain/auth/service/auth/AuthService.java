package com.gaoyifeng.IDaaS.domain.auth.service.auth;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.BeanToMapCopier;
import cn.hutool.jwt.JWT;
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

//        userAccountRepository.deleteCacheCode(account, type);

        // 生产令牌
        log.info("生产令牌");
        String token = jwtUtils.encode(userAccount.getFlakeSnowId(), 24 * 60 * 60 * 1000, BeanUtil.beanToMap(userAccount));
        HttpThreadLocalUtil.getResponse().addHeader("Authorization", token);

        //refreshtoken
        UserFreshTokenEntity userFreshTokenEntity = userFreshTokenRepository.get(userAccount.getFlakeSnowId());
        //为空 则创建
        if(userFreshTokenEntity==null){
            String freshToken = "freshToken_test";
            userFreshTokenRepository.save(freshToken,userAccount.getFlakeSnowId());
            HttpThreadLocalUtil.getResponse().addHeader("refreshToken", freshToken);
        }
    }

    @Override
    public Map verify(String token) {
        if(jwtUtils.isVerify(token)){
            return jwtUtils.decode(token);
        }
        throw new BaseException(Constants.ResponseCode.ILLEGAL_PARAMETER, "token非法");
    }

    @Override
    public void renewval(String token, String refreshToken) {
         //判断refreshToken是否正确
        UserFreshTokenEntity userFreshTokenEntity = userFreshTokenRepository.get(jwtUtils.decode(token).get("flakeSnowId").toString());
        if(userFreshTokenEntity!=null){
            if(refreshToken.equals(userFreshTokenEntity.getFreshToken())){
                //生产新的token
                // 生产令牌
                log.info("生产令牌");
                String userFlakeSnowId = userFreshTokenEntity.getUserFlakeSnowId();
                UserAccountEntity userAccount = userAccountRepository.selectUserByFlakeSnowId(userFlakeSnowId);
                token = jwtUtils.encode(userFlakeSnowId, 24 * 60 * 60 * 1000, BeanUtil.beanToMap(userAccount));
                HttpThreadLocalUtil.getResponse().addHeader("Authorization", token);
                return;
            }
        }
        throw new BaseException(Constants.ResponseCode.ILLEGAL_PARAMETER, "refreshToken非法");
    }

}
