package com.gaoyifeng.IDaaS.domain.auth.service;

public interface ICodeService {

    void getCode(String account, String type);

    void validCode(String flakeSnowId,String account, String code, String type);

}
