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


    /**
     * 这里就是产生jwt字符串的地方
     * jwt字符串包括三个部分
     * 1. header
     * -当前字符串的类型，一般都是“JWT”
     * -哪种算法加密，“HS256”或者其他的加密算法
     * 所以一般都是固定的，没有什么变化
     * 2. payload
     * 一般有四个最常见的标准字段（下面有）
     * iat：签发时间，也就是这个jwt什么时候生成的
     * jti：JWT的唯一标识
     * iss：签发人，一般都是username或者userId
     * exp：过期时间
     */
    public String encode(String issuer, long ttlMillis, Map<String, Object> claims) {
        // iss签发人，ttlMillis生存时间，claims是指还想要在jwt中存储的一些非隐私信息
        if (claims == null) {
            claims = new HashMap<>();
        }

        String token = JWTUtil.createToken(claims, SECRET_KEY.getBytes());

        // 放入缓存
        jwtRepository.put(issuer, token);
        return token;
    }

    // 相当于encode的方向，传入jwtToken生成对应的username和password等字段。Claim就是一个map
    // 也就是拿到荷载部分所有的键值对
    public Map<String,String> decode(String jwtToken) {
        // 得到 DefaultJwtParser
        JWT jwt = JWTUtil.parseToken(jwtToken);
        JWTPayload jwtPayload = jwt.getPayload();

        String userFlakeSnowId = jwtPayload.getClaim("userFlakeSnowId").toString();

        // 验证缓存
        String cacheJwt = jwtRepository.get(userFlakeSnowId);
        if (!cacheJwt.equals(jwtToken)) {
            throw new BaseException(Constants.ResponseCode.ILLEGAL_PARAMETER, "token非法");
        }

        Map<String,String> body = new HashMap<>();
        return body;
    }

    // 判断jwtToken是否合法
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