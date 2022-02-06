package com.ldg.service.impl.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.concurrent.TimeUnit;

/**
 * @author Administrator
 */
@Component
public class MQRedis {

    private static final String DYNAMICS_CACHE_KEY="dynamics";
    private static final String GOODS_CACHE_KEY="goods";
    private static final String USERINFO_CACHE_KEY="userInfo:";

    private RedisTemplate<String,Object> redisTemplate;

    @Autowired
    public MQRedis(RedisTemplate<String, Object> redisTemplate){
        this.redisTemplate=redisTemplate;
    }


    /**
     *发送消息到MQ前缓存，确保100%发送消息
     * 结合定时任务
     */
    public void cacheMessageToRedis(String mId, Object message){
        redisTemplate.opsForValue().set(mId,message);
    }
    /**
     *发送消息到MQ前缓存，确保100%发送消息,成功后删除
     * 结合定时任务
     */
    public void deleteMessageToRedis(String mId){
        redisTemplate.delete(mId);
    }
    /**
     *消费者消费消息后,把消息组键放到redis防止重复消费
     */
    public void cacheMessageIdToRedis(String mId,Object object){
        redisTemplate.opsForValue().set(mId,object,60L,TimeUnit.MINUTES);
    }
    /**
     * 消息幂等性校验，防止重复消费
     */
    public boolean getMessageIdFromRedis(String mId){
        Object o = redisTemplate.opsForValue().get(mId);
        return StringUtils.isEmpty(o);
    }

    /***
     * 通过消息ID从缓存获取消息
     * @param mId
     * @return
     */
    public Object getMessageFromRedis(String mId){
        Object o = redisTemplate.opsForValue().get(mId);
        return o;
    }


    /**
     *设置key的过期时间
     */
    public  boolean expire(String key, int time,TimeUnit seconds){
        if(StringUtils.isEmpty(key)){
            return false;
        }else{
         return Boolean.TRUE.equals(redisTemplate.expire(key, time, seconds));
        }
    }
    /**
     *删除指定的key
     */
    public  boolean deleteKey(String key){
//        redisTemplate.delete(key);
//        return  true;
        if(StringUtils.isEmpty(key)){
            return false;
        }else{
             return Boolean.TRUE.equals(redisTemplate.delete(key));
        }
    }








}
