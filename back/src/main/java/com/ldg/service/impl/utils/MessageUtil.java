package com.ldg.service.impl.utils;

import com.baomidou.mybatisplus.core.toolkit.Sequence;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ldg.config.rabbitmq.RabbitmqQueueConfig;
import com.ldg.pojo.Dynamic;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

/**
 * @author Administrator
 */
@Component
public class MessageUtil {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private MQRedis mqRedis;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private AmqpAdmin rabbitAdmin;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private Sequence  sequence;

//    @Autowired
//    MessageUtil(RabbitTemplate rabbitTemplate, MQRedis redisUtil){
//        this.rabbitTemplate=rabbitTemplate;
//        this.redisUtil=redisUtil;
//    }

    /**
     * 应该用分布式事务解决 redisUtil和rabbitTemplate中任何一个失败的问题。
     * 此处先不解决
     * @param message
     * @return
     */

    public boolean send(Object message) {
        String exchange;
        String routingKey;
        if(message instanceof Dynamic){
            exchange=RabbitmqQueueConfig.ORDINARY_DYNAMIC_EXCHANGE;
            routingKey=RabbitmqQueueConfig.ORDINARY_DYNAMIC_ROUTINGKEY;
        }else{
            exchange=RabbitmqQueueConfig.ORDINARY_GOODS_EXCHANGE;
            routingKey=RabbitmqQueueConfig.ORDINARY_GOODS_ROUTINGKEY;
        }
        System.out.println("发送消息执行了+----------------MessageUtils");
        /**用雪花算法生成消息的唯一标识*/
        long l = sequence.nextId();
        String mId= String.valueOf(l);
        /**将消息缓存到redis，确保一定发送成功*/
        mqRedis.cacheMessageToRedis(mId,message);
        try {
            byte[] bytes = objectMapper.writeValueAsString(message).getBytes(StandardCharsets.UTF_8);
            rabbitTemplate.convertAndSend(exchange,routingKey,bytes,properties->{
                properties.getMessageProperties().setHeader("messageId",mId);
                properties.getMessageProperties().setMessageId(mId);
                return properties;
            },new CorrelationData(mId));
        }catch (Exception e){
            e.printStackTrace();
        }
        return true;
    }


}
