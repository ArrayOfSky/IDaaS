package com.gaoyifeng.IDaaS.domain.auth.service.code.message;

import com.gaoyifeng.IDaaS.domain.auth.service.code.ISendMessageService;
import org.springframework.stereotype.Service;

/**
 * 发送短信
 */
@Service
public class MessageSendService implements ISendMessageService {
    @Override
    public void sendMessage(String to, String content) {

    }
}
