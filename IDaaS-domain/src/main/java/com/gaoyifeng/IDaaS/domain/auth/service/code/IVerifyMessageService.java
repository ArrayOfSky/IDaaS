package com.gaoyifeng.IDaaS.domain.auth.service.code;



public interface IVerifyMessageService {

    void verifyMessage(String flakeSnowId,String account);

}
