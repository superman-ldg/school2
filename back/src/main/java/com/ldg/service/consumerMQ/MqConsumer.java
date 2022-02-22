package com.ldg.service.consumerMQ;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ldg.config.rabbitmq.RabbitmqQueueConfig;
import com.ldg.pojo.Dynamic;
import com.ldg.pojo.Goods;
import com.ldg.service.impl.DynamicServiceImpl;
import com.ldg.service.impl.GoodsServiceImpl;
import com.ldg.service.impl.utils.MQRedis;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * @author Administrator
 */
@Component
public class MqConsumer {

    @Autowired
    private MQRedis redisUtil;
    @Autowired
    private DynamicServiceImpl dynamicService;
    @Autowired
    private GoodsServiceImpl goodsService;

    @Autowired
    private ObjectMapper objectMapper;

    /**消费普通队列动态，成功插入时会有缓存一致性问题，解决缓存一致性问题，如果不成功则丢到死信队列，让通信服务器通知用户
     * 防止消息重复消费
     * */
    @RabbitListener(queuesToDeclare =@Queue(name = RabbitmqQueueConfig.DEAD_DYNAMIC_QUEUE,durable = "true",autoDelete = "false"))
    public void ordinaryQueueDynamicConsumer(Message message,Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) Long tag) throws IOException {
        String s = new String(message.getBody(), StandardCharsets.UTF_8);
        Dynamic dynamic = objectMapper.readValue(s, Dynamic.class);
        String messageId = message.getMessageProperties().getMessageId();
        try{
            /**
             * 消息的幂等性验证
             */
            boolean isEmpty = redisUtil.getMessageIdFromRedis(messageId);
            if(isEmpty){
                boolean result = dynamicService.insertDynamic(dynamic);
                if(!result){throw new RuntimeException();}
                /**
                 *缓存消息防止重复消费
                 */
                redisUtil.cacheMessageIdToRedis(messageId,dynamic);
            }
            channel.basicAck(tag,false);
        }catch (Exception e){
            channel.basicNack(tag,false,false);
        }
    }
    /**消费普通队列物品，成功插入时会有缓存一致性问题，解决缓存一致性问题，如果不成功则丢到死信队列，让通信服务器通知用户
     *
     * 防止消息重复消费
     * */
   @RabbitListener(queuesToDeclare = @Queue(name = RabbitmqQueueConfig.ORDINARY_GOODS_QUEUE,durable = "true",autoDelete = "false"))
    public void ordinaryQueueGoodsConsumer(Message message,Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) Long tag) throws IOException {

        String s = new String(message.getBody(), StandardCharsets.UTF_8);
        Goods good = objectMapper.readValue(s, Goods.class);
        System.out.println("goods:"+good);
        String messageId = message.getMessageProperties().getMessageId();
        try{
            /**
             * 消息的幂等性验证
             */
            boolean isEmpty = redisUtil.getMessageIdFromRedis(messageId);
            if(isEmpty) {
                boolean id= goodsService.insert(good);
                if (!id) {
                    throw new RuntimeException();
                }
                /**
                 *缓存消息防止重复消费
                 */
                redisUtil.cacheMessageIdToRedis(messageId,good);
            }
            channel.basicAck(tag, false);
        }catch (Exception e){
            channel.basicNack(tag,false,false);
        }
    }

//    @RabbitListener(queuesToDeclare = @Queue(name = RabbitmqQueueConfig.DEAD_DYNAMIC_QUEUE,durable = "false",autoDelete = "false"))
//    public void deadQueueDynamicConsumer(Dynamic dynamic, Channel channel,@Header(AmqpHeaders.DELIVERY_TAG) Long tag) throws IOException {
//        try{
//
//        }catch (Exception e){
//            channel.basicNack(tag,false,false);
//        }
//    }
//
//    @RabbitListener(queuesToDeclare = @Queue(name = RabbitmqQueueConfig.DEAD_GOODS_QUEUE,durable = "false",autoDelete = "false"))
//    public void deadQueueGoodsConsumer(Goods good,Channel channel,@Header(AmqpHeaders.DELIVERY_TAG) Long tag) throws IOException {
//        try{
//
//        }catch (Exception e){
//            channel.basicNack(tag,false,false);
//        }
//    }


}
