package com.ldg.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ldg.config.rabbitmq.RabbitmqQueueConfig;
import com.ldg.pojo.Dynamic;
import com.ldg.service.impl.utils.MQRedis;
import lombok.SneakyThrows;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;
import java.nio.charset.StandardCharsets;

/**
 * @author Administrator
 */
@Component
public class ListenerMq implements ApplicationListener<EventMq> {

    @Autowired
    private MQRedis mqRedis;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @SneakyThrows
    @Override
    public void onApplicationEvent(EventMq eventMq) {
        String msgId=eventMq.getMsgId();
        Object message = mqRedis.getMessageFromRedis(msgId);
        String exchange;
        String routingKey;
        if(message instanceof Dynamic){
            exchange= RabbitmqQueueConfig.ORDINARY_DYNAMIC_EXCHANGE;
            routingKey=RabbitmqQueueConfig.ORDINARY_DYNAMIC_ROUTINGKEY;
        }else{
            exchange=RabbitmqQueueConfig.ORDINARY_GOODS_EXCHANGE;
            routingKey=RabbitmqQueueConfig.ORDINARY_GOODS_ROUTINGKEY;
        }
        try {
            byte[] bytes = objectMapper.writeValueAsString(message).getBytes(StandardCharsets.UTF_8);
            rabbitTemplate.convertAndSend(exchange,routingKey,bytes,properties->{
                properties.getMessageProperties().setHeader("messageId",msgId);
                properties.getMessageProperties().setMessageId(msgId);
                return properties;
            },new CorrelationData(msgId));
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
