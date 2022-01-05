package com.ldg.utils;

import com.ldg.pojo.Dynamic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.*;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @author 梁登光
 */
@Component
public class DynamicRedis {

    private static final String DYNAMICS_CACHE_KEY="dynamics";
    private static final String DYNAMICS_ZAN_KEY="dynamicsZan";
    private RedisTemplate<String,Object> redisTemplate;
    private HashOperations<String,String,Object>  redisForHash;

    @Autowired
    DynamicRedis(RedisTemplate<String, Object> redisTemplate,HashOperations<String,String,Object> redisForHash){
        this.redisTemplate=redisTemplate;
        this.redisForHash=redisForHash;
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
    public Long getCacheDynamicZan(Long id){
        String key="DynamicZan:"+id;
        Long o = (Long)redisTemplate.opsForValue().get(key);
        return o;
    }

    /**
     * 取消点赞操作*/
    public void dynamicNZan(Dynamic dynamic){
        if(!StringUtils.isEmpty(dynamic)){
            String key="DynamicZan:"+dynamic.getId();
            redisTemplate.opsForValue().increment(key);
        }
    }

    /***
     *
     * scan非阻塞redis操作，防止redis的过多key造成redis扫描时停顿阻塞其它请求
     *
     *      Cursor<Map.Entry<Object, Object>> scan2 = redisTemplate.opsForHash().scan("", null);
     *         Cursor<ZSetOperations.TypedTuple<Object>> scan1 = redisTemplate.opsForZSet().scan("", null);
     *         Cursor<Object> scan = redisTemplate.opsForSet().scan("", null);
     */

    /**获取所有缓存在redis的点赞*/
    public List<String> getAllKeys(){
        String key="DynamicZan:*";
        ScanOptions options=ScanOptions.scanOptions().count(10).match(key).build();
        RedisSerializer<String> redisSerializer = (RedisSerializer<String>) redisTemplate.getKeySerializer();
        Cursor cursor = (Cursor) redisTemplate.executeWithStickyConnection(redisConnection -> new ConvertingCursor<>(redisConnection.scan(options), redisSerializer::deserialize));
        List<String> result = new ArrayList<>();
        while(cursor.hasNext()){
            result.add(Objects.requireNonNull(cursor.next()).toString());
        }
        //切记这里一定要关闭，否则会耗尽连接数。
        try {
            cursor.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }


    /**获取所有缓存在redis的点赞*/
    public Map<Object,Object> getAllMap(){
        Map<Object,Object> map = redisTemplate.opsForHash().entries(DYNAMICS_ZAN_KEY);
        return map;

    }
    /**获取所有缓存在redis的点赞*/
    public long getOneDynamicZan(Long id){
       return (long)redisTemplate.opsForHash().get(DYNAMICS_ZAN_KEY, String.valueOf(id));
    }

    /**
     * 点赞操作*/
    public Long cacheDynamicZan(Long id){
        //发现hash更好用，改用hash缓存。
//        String key="DynamicZan:"+id;
//        if(!StringUtils.isEmpty(redisTemplate.opsForValue().get(key))){
//            return redisTemplate.opsForValue().increment(key);
//        }else{
//            redisTemplate.opsForValue().set(key,fabulous);
//            return fabulous+1L;
//        }

        long count=1L;
        if(StringUtils.isEmpty(redisForHash.get(DYNAMICS_ZAN_KEY, String.valueOf(id)))){
            redisForHash.put(DYNAMICS_ZAN_KEY,String.valueOf(id),1L);
        }else{
            count= redisForHash.increment(DYNAMICS_ZAN_KEY, String.valueOf(id), 1L);
        }
        return count;
    }


    public void test(){
        for(int i=0;i<10;i++){
            cacheDynamicZan((long) i);
        }
    }






}
