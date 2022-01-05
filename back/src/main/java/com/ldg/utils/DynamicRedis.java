package com.ldg.utils;

import com.ldg.pojo.Dynamic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author 梁登光
 */
@Component
public class DynamicRedis {
    private static final String DYNAMICS_CACHE_KEY="dynamics";
    private RedisTemplate<String,Object> redisTemplate;

    @Autowired
    DynamicRedis(RedisTemplate<String, Object> redisTemplate){
        this.redisTemplate=redisTemplate;
    }

    /**
     * 从缓存获取动态*/
    public List<Dynamic> getDynamics(){
        return (List<Dynamic>) redisTemplate.opsForValue().get(DYNAMICS_CACHE_KEY);
    }

    /**缓存多条动态
     */
    public <T> void cacheDynamics(List<T> data){
        if(!StringUtils.isEmpty(data)){
            redisTemplate.opsForValue().setIfPresent(DYNAMICS_CACHE_KEY,data,30L,TimeUnit.MINUTES);
        }
    }
    public void deleteDynamics(){
        redisTemplate.delete(DYNAMICS_CACHE_KEY);
    }

    /**
     * 缓存动态点赞*/
    public void cacheDynamic(Dynamic dynamic){
        if(!StringUtils.isEmpty(dynamic)){
            String key="DynamicZan:"+dynamic.getId();
            redisTemplate.opsForValue().set(key,dynamic.getFabulous(),24L, TimeUnit.HOURS);
        }
    }
    /**
     * 点赞操作*/
    public void dynamicZan(Dynamic dynamic){
        if(!StringUtils.isEmpty(dynamic)){
            String key="DynamicZan:"+dynamic.getId();
            redisTemplate.opsForValue().increment(key);
        }
    }
    /**
     * 取消点赞操作*/
    public void dynamicNZan(Dynamic dynamic){
        if(!StringUtils.isEmpty(dynamic)){
            String key="DynamicZan:"+dynamic.getId();
            redisTemplate.opsForValue().increment(key);
        }
    }
}
