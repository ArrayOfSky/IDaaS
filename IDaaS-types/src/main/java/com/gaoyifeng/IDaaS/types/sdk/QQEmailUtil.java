package com.gaoyifeng.IDaaS.types.sdk;

import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import java.io.File;

@Component
public class QQEmailUtil {

    @Value("${spring.mail.username}")
    private String sendFromEmail;

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private FreeMarkerConfigurer markerConfigurer;

    public void sendStringEmail(String receiverName, String title, String content) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(sendFromEmail);
        message.setTo(receiverName);
        message.setSubject(title);
        message.setText(content);
        mailSender.send(message);
    }

    //发送大文件/附件的邮件
    public void sendBigEmail(String receiverName, String title, String content, File file) {
        MimeMessage message = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message,true);
            helper.setFrom(sendFromEmail);
            helper.setTo(receiverName);
            helper.setSubject(title);
            helper.setText(content);
            FileSystemResource resource = new FileSystemResource(file);
            helper.addAttachment("附件", resource);
        }catch (Exception e){
            e.printStackTrace();
        }
        mailSender.send(message);
    }


}
