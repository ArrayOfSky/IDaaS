package com.gaoyifeng.IDaaS.service;

import com.gaoyifeng.IDaaS.utils.SignatureUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


@Service
public class WeiXinValidateService {
    @Value("${wx.config.token}")
    private String token;

    public boolean checkSign(String signature, String timestamp, String nonce) {
        return SignatureUtil.check(token, signature, timestamp, nonce);
    }
}
