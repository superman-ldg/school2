package com.ldg.config.rabbitmq;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Administrator
 */
@Configuration
public class RabbitmqQueueConfig {

    /**
     * 普通队列
     * 最大长度
     * 持久化
     */
    public static final String ORDINARY_DYNAMIC_QUEUE="campus_ordinary_dynamic_queue";
    public static final String ORDINARY_DYNAMIC_EXCHANGE="campus_ordinary_dynamic_exchange";
    public static final String ORDINARY_DYNAMIC_ROUTINGKEY="campus.ordinary.dynamic.routingKey.#";

    public static final String DEAD_DYNAMIC_QUEUE="campus_dead_dynamic_queue";
    public static final String DEAD_DYNAMIC_EXCHANGE="campus_dead_dynamic_exchange";
    public static final String DEAD_DYNAMIC_ROUTINGKEY="campus.dead.dynamic.routingKey.#";

    public static final String ORDINARY_GOODS_QUEUE="campus_ordinary_goods_queue";
    public static final String ORDINARY_GOODS_EXCHANGE="campus_ordinary_goods_exchange";
    public static final String ORDINARY_GOODS_ROUTINGKEY="campus.ordinary.goods.routingKey.#";

    public static final String DEAD_GOODS_QUEUE="campus_dead_goods_queue";
    public static final String DEAD_GOODS_EXCHANGE="campus_dead_goods_exchange";
    public static final String DEAD_GOODS_ROUTINGKEY="campus.dead.goods.routingKey.#";

    /**
     * 最大队列长度设置为20万
     * 消息的最大长度6000字节(B),按3B个一汉字最大约2000字。
     *   * 队列的其他属性参数
     *      * （1）x-message-ttl：消息的过期时间，单位：毫秒；
     *      *（2）x-expires：队列过期时间，队列在多长时间未被访问将被删除，单位：毫秒；
     *      *（3）x-max-length：队列最大长度，超过该最大值，则将从队列头部开始删除消息；
     *      *（4）x-max-length-bytes：队列消息内容占用最大空间，受限于内存大小，超过该阈值则从队列头部开始删除消      * 息；
     *      *（5）x-overflow：设置队列溢出行为。这决定了当达到队列的最大长度时消息会发生什么。有效值是drop-
     *      * head、reject-publish或reject-publish-dlx。仲裁队列类型仅支持drop-head；
     *      *（6）x-dead-letter-exchange：死信交换器名称，过期或被删除（因队列长度超长或因空间超出阈值）的消息
     *      * 可指定发送到该交换器中；
     *      *（7）x-dead-letter-routing-key：死信消息路由键，在消息发送到死信交换器时会使用该路由键，如果不设
     *      * 置，则使用消息的原来的路由键值
     *      *（8）x-single-active-consumer：表示队列是否是单一活动消费者，true时，注册的消费组内只有一个消费
     *      * 者消费消息，其他被忽略，false时消息循环分发给所有消费者(默认false)
     *      *（9）x-max-priority：队列要支持的最大优先级数;如果未设置，队列将不支持消息优先级；
     *      *（10）x-queue-mode（Lazy mode）：将队列设置为延迟模式，在磁盘上保留尽可能多的消息，以减少RAM的使
     *      * 用;如果未设置，队列将保留内存缓存以尽可能快地传递消息；
     *      *（11）x-queue-master-locator：在集群模式下设置镜像队列的主节点信息。
     */
    @Bean("campus_ordinary_dynamic_queue")
    public Queue campus_ordinary_dynamic_queue()
    {
       Map<String,Object> map=new HashMap<>(16);
       map.put("x-max-length",200000);
       map.put("x-message-ttl",50000);
       map.put("x-max-length-bytes",6000);
       map.put("x-dead-letter-exchange",DEAD_DYNAMIC_EXCHANGE);
       map.put("x-dead-letter-routing-key",DEAD_DYNAMIC_ROUTINGKEY);
       return new Queue(ORDINARY_DYNAMIC_QUEUE,false,false,false,map);
    }
    @Bean("campus_ordinary_dynamic_exchange")
    public TopicExchange campus_ordinary_dynamic_exchange(){
        return new TopicExchange(ORDINARY_DYNAMIC_EXCHANGE,false,false);
    }

    @Bean("campus_dead_dynamic_queue")
    public Queue campus_dead_dynamic_queue()
    {
        Map<String,Object> map=new HashMap<>(16);
        map.put("x-max-length",20000);
        map.put("x-max-length-bytes",6000);
        return new Queue(DEAD_DYNAMIC_QUEUE,false,false,false,map);
    }
    @Bean("campus_dead_dynamic_exchange")
    public TopicExchange campus_dead_dynamic_exchange(){
        return new TopicExchange(DEAD_DYNAMIC_EXCHANGE,false,false);
    }


    @Bean("campus_ordinary_goods_queue")
    public Queue campus_ordinary_goods_queue()
    {
        Map<String,Object> map=new HashMap<>(16);
        map.put("x-max-length",20000);
        map.put("x-max-length-bytes",6000);
        map.put("x-message-ttl",5000);
        map.put("x-dead-letter-exchange",DEAD_GOODS_EXCHANGE);
        map.put("x-dead-letter-routing-key",DEAD_GOODS_ROUTINGKEY);
        return new Queue( ORDINARY_GOODS_QUEUE,false,false,false,map);
    }
//
    @Bean("campus_ordinary_goods_exchange")
    public TopicExchange campus_ordinary_goods_exchange(){
        return new TopicExchange(ORDINARY_GOODS_EXCHANGE,false,false);
    }
    @Bean("campus_dead_goods_queue")
    public Queue campus_dead_goods_queue()
    {
        Map<String,Object> map=new HashMap<>(16);
        map.put("x-max-length",20000);
        return new Queue(DEAD_GOODS_QUEUE,false,false,false,map);
    }
    @Bean("campus_dead_goods_exchange")
    public TopicExchange campus_dead_goods_exchange(){
        return new TopicExchange(DEAD_GOODS_EXCHANGE,false,false);
    }

    @Bean
    public Binding ordinaryDynamicQueueAndExchange(){
        return BindingBuilder.bind(campus_ordinary_dynamic_queue()).
                to(campus_ordinary_dynamic_exchange()).with(ORDINARY_DYNAMIC_ROUTINGKEY);
    }
    @Bean
    public Binding deadDynamicQueueAndExchange(){
        return BindingBuilder.bind(campus_dead_dynamic_queue()).to(campus_dead_dynamic_exchange()).with(DEAD_DYNAMIC_ROUTINGKEY);
    }

    @Bean
    public Binding ordinaryGoodsQueueAndExchange(){
        return BindingBuilder.bind(campus_ordinary_goods_queue()).
                to(campus_ordinary_goods_exchange()).with(ORDINARY_GOODS_ROUTINGKEY);
    }
    @Bean
    public Binding deadGoodsQueueAndExchange(){
        return BindingBuilder.bind(campus_dead_goods_queue()).to(campus_dead_goods_exchange()).with(DEAD_GOODS_ROUTINGKEY);
    }




}
