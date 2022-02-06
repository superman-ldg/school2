package com.ldg.config.rabbitmq;

import com.ldg.listener.EventMq;
import com.ldg.listener.PublishMq;
import com.ldg.service.impl.utils.MQRedis;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Administrator
 */
@Component
public class MyConfirmCallback implements RabbitTemplate.ConfirmCallback {

    @Autowired
    private MQRedis mqRedis;

    @Autowired
    private PublishMq publishMq;

    @Autowired
    public MyConfirmCallback(MQRedis redisUtil){
        this.mqRedis=redisUtil;
    }

    /**发送消息成功，从redis删除消息缓存*/
    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        String id = correlationData.getId();
        if(ack){
            mqRedis.deleteMessageToRedis(correlationData.getId());
            System.out.println("生成者端的id为："+correlationData.getId());
        }else{
            publishMq.publishEvent(new EventMq(id,null));
        }
    }
}
