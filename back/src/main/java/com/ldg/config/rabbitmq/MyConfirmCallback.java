package com.ldg.config.rabbitmq;

import com.ldg.utils.MQRedis;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Administrator
 */
@Component
public class MyConfirmCallback implements RabbitTemplate.ConfirmCallback {


    private MQRedis redisUtil;

    @Autowired
    public MyConfirmCallback(MQRedis redisUtil){
        this.redisUtil=redisUtil;
    }

    /**发送消息成功，从redis删除消息缓存*/
    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        if(ack){
            redisUtil.deleteMessageToRedis(correlationData.getId());
            System.out.println("生成者端的id为："+correlationData.getId());
        }else{
            System.out.println("发送消息失败了，消失没有能到达交换机");
        }
    }
}
