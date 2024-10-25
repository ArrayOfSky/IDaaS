package com.gaoyifeng.IDaaS.config;

import com.gaoyifeng.IDaaS.types.enums.RabbitMqModel;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Bean
    public Queue emailQueue(){
        return new Queue(RabbitMqModel.EMAIL_QUEUE);
    }

    @Bean
    public TopicExchange emailExchange(){
        return new TopicExchange(RabbitMqModel.EMAIL_EXCHANGE);
    }

    @Bean
    public Binding emailBinding(){
        return BindingBuilder.bind(emailQueue()).to(emailExchange()).with(RabbitMqModel.EMAIL_KEY+"#");
    }

}
