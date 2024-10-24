package com.gaoyifeng.IDaaS.domain.auth.service.code;

/**
 * 发送验证码策略类
 */
public interface ISendMessageService {

    void sendMessage(String to,String content);

}
