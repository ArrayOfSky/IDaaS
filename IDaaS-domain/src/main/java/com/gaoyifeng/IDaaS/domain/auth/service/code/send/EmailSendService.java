package com.gaoyifeng.IDaaS.domain.auth.service.code.send;

import com.gaoyifeng.IDaaS.domain.auth.service.code.ISendMessageService;
import com.gaoyifeng.IDaaS.types.sdk.QQEmailUtil;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 发送邮箱
 */
@Service
@Slf4j
public class EmailSendService implements ISendMessageService {

    @Resource
    private QQEmailUtil qqEmailUtil;

    private final String title = "gaoyifeng博客";

    private final String contentTemplate =
        """
        您的验证码是: %s. 请在5分钟内使用。
        """;

    @Override
    public void sendMessage(String to, String content) {
        String formattedContent = String.format(contentTemplate, content);
        qqEmailUtil.sendStringEmail(to, title, formattedContent);
        log.info("发送邮件成功: {}", to);
    }




}
