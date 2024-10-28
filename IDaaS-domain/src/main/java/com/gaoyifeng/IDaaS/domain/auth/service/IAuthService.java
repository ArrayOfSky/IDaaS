package com.gaoyifeng.IDaaS.domain.auth.service;

import java.util.Map;

public interface IAuthService {

    void login(String account, String password,String type);

    Map<String,String> verify(String token);

    void renewval(String token, String refreshToken);
}
