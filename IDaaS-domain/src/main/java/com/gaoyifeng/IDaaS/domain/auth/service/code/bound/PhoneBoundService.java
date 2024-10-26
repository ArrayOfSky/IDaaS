package com.gaoyifeng.IDaaS.domain.auth.service.code.bound;

import com.gaoyifeng.IDaaS.domain.auth.service.code.IVerifyMessageService;
import org.springframework.stereotype.Service;


@Service
public class PhoneBoundService implements IVerifyMessageService {
    @Override
    public void verifyMessage(String flakeSnowId, String account) {

    }

}
