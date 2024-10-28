package com.gaoyifeng.IDaaS.domain.auth.service.auth.jwt;


import cn.hutool.jwt.JWT;
import cn.hutool.jwt.JWTPayload;
import cn.hutool.jwt.JWTUtil;
import com.gaoyifeng.IDaaS.domain.auth.repository.IJwtRepository;
import com.gaoyifeng.IDaaS.types.commom.Constants;
import com.gaoyifeng.IDaaS.types.exception.BaseException;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Component
@Slf4j
public class JwtUtils {

    @Value("${jwt.secret.key}")
    private String SECRET_KEY;

    @Resource
    private IJwtRepository jwtRepository;


    public String encode(String issuer, Map<String, Object> claims) {
        // iss签发人，ttlMillis生存时间，claims是指还想要在jwt中存储的一些非隐私信息
        if (claims == null) {
            claims = new HashMap<>();
        }

        String token = JWTUtil.createToken(claims, SECRET_KEY.getBytes());
        // 放入缓存
        jwtRepository.put(issuer, token);
        return token;
    }

    public Map<String,String> decode(String jwtToken) {
        // 得到 DefaultJwtParser
        JWT jwt = JWTUtil.parseToken(jwtToken);
        JWTPayload jwtPayload = jwt.getPayload();

        String userFlakeSnowId = (String) jwtPayload.getClaim("flakeSnowId");

        // 验证缓存
        String cacheJwt = jwtRepository.get(userFlakeSnowId);
        if (cacheJwt==null||!cacheJwt.equals(jwtToken)) {
            throw new BaseException(Constants.ResponseCode.ILLEGAL_PARAMETER, "token非法");
        }

        Map<String,String> body = new HashMap<>();
        return body;
    }

    public boolean isVerify(String jwtToken) {
        try {
            JWTUtil.verify(jwtToken, SECRET_KEY.getBytes());
            // 校验不通过会抛出异常
            // 判断合法的标准：1. 头部和荷载部分没有篡改过。2. 没有过期
            return true;
        } catch (Exception e) {
            log.error("jwt isVerify Err", e);
            return false;
        }

    }


}