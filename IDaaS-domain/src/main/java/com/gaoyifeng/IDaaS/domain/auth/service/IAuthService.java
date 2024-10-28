package com.gaoyifeng.IDaaS.domain.auth.service;

import java.util.Map;

public interface IAuthService {

    String login(String account, String password,String type);

    Map verify(String token);

    Map renewval(String token, String refreshToken);
}
