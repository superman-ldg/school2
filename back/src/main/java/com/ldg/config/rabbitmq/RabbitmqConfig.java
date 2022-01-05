package com.ldg.config.rabbitmq;

import com.rabbitmq.client.*;
import org.springframework.amqp.rabbit.AsyncRabbitTemplate;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.connection.PendingConfirm;
import org.springframework.amqp.rabbit.connection.PublisherCallbackChannel;
import org.springframework.amqp.rabbit.connection.PublisherCallbackChannelImpl;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeoutException;

/**
 * @author Administrator
 */
@Configuration
public class RabbitmqConfig {

//   // @Bean
//    public RabbitTemplate rabbitTemplate() {
//        RabbitTemplate rabbitTemplate=new RabbitTemplate();
//
//        // 消息发送失败返回到队列中, yml需要配置 publisher-returns: true
//        rabbitTemplate.setMandatory(true);
//       //rabbitTemplate.addListener();
//
//        // 消息返回, yml需要配置 publisher-returns: true
//        rabbitTemplate.setReturnCallback((message, replyCode, replyText, exchange, routingKey) -> {
//            String correlationId = message.getMessageProperties().getCorrelationId();
//            System.out.println("消息"+correlationId+" 发送失败"+"应答码：" +replyCode+replyText+exchange+routingKey);
//        });
//
//        // 消息确认, yml需要配置 publisher-confirms: true
//        rabbitTemplate.setConfirmCallback((correlationData, ack, cause) -> {
//            if (ack) {
//                System.out.println("消息发送到exchange成功,id:"+correlationData.getId());
//            } else {
//                System.out.println("消息发送到exchange失败,原因:"+cause);
//            }
//        });
//        rabbitTemplate.setMessageConverter(messageConverter());
//        return rabbitTemplate;
//    }
//
//    @Bean
//    public MessageConverter messageConverter(){
//        return new Jackson2JsonMessageConverter();
//    }
//
//    @Bean
//    public AsyncRabbitTemplate asyncRabbitTemplate(RabbitTemplate rabbitTemplate){
//        return new AsyncRabbitTemplate(rabbitTemplate);
//    }


}
