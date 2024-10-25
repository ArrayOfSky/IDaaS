package com.gaoyifeng.IDaaS.trigger.mq;

import com.alibaba.fastjson.JSON;
import com.gaoyifeng.IDaaS.domain.auth.model.entity.CodeSendEntity;
import com.gaoyifeng.IDaaS.domain.auth.service.ICodeService;
import com.gaoyifeng.IDaaS.domain.auth.service.code.CodeService;
import com.gaoyifeng.IDaaS.types.enums.RabbitMqModel;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class MessageSenderConsumer {

    @Resource
    private ICodeService codeService;

    @RabbitListener(queues = RabbitMqModel.EMAIL_QUEUE)
    public void receive(String message) {
        CodeSendEntity codeSendEntity = JSON.parseObject(message, CodeSendEntity.class);
        try{
            System.out.println("receive:" + codeSendEntity);
            codeService.sendCode(codeSendEntity);
        }catch (Exception e){
            log.error("消息消费失败", codeSendEntity);
            e.printStackTrace();
        }
    }

}
