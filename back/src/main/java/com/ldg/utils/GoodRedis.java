package com.ldg.utils;

import com.ldg.pojo.Goods;
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
public class GoodRedis {
    private static final String GOODS_CACHE_KEY="goods";

    private RedisTemplate<String,Object> redisTemplate;

    @Autowired
    GoodRedis(RedisTemplate<String, Object> redisTemplate){
        this.redisTemplate=redisTemplate;
    }

    /**
     * String 的操作从缓存获取物品*/
    public List<Goods> getGoods(){
        return (List<Goods>)redisTemplate.opsForValue().get(GOODS_CACHE_KEY);
    }


    /**缓存多条物品
     */
    public <T> void cacheGoods(List<T> data){
        if(!StringUtils.isEmpty(data)){
            redisTemplate.opsForValue().setIfPresent(GOODS_CACHE_KEY,data,30L, TimeUnit.MINUTES);
        }
    }
    public void deleteGoods(){
        redisTemplate.delete(GOODS_CACHE_KEY);
    }

    /**
     * 缓存单个物品*/
    public void cacheUserGood(Goods goods){
        if(!StringUtils.isEmpty(goods)){
            String key="userGoods:"+goods.getId();
            redisTemplate.opsForValue().setIfPresent(key,goods,60L,TimeUnit.MINUTES);
        }
    }

}
