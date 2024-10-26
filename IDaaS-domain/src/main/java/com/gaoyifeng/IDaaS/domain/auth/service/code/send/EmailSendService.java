package com.gaoyifeng.IDaaS.domain.auth.service.code.send;

import com.gaoyifeng.IDaaS.domain.auth.service.code.ISendMessageService;
import com.gaoyifeng.IDaaS.types.sdk.QQEmailUtil;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * 发送邮箱
 */
@Service
public class EmailSendService implements ISendMessageService {

    @Resource
    private QQEmailUtil qqEmailUtil;

    private final String title = "gaoyifeng博客-验证码";

    private final String contentTemplate = """
        您的验证码是: %s. 请在5分钟内使用。
        """;

    @Override
    public void sendMessage(String to, String content) {
        String formattedContent = String.format(contentTemplate, content);
        qqEmailUtil.sendStringEmail(to, title, formattedContent);
    }




}
