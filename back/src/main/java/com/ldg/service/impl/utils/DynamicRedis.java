package com.ldg.service.impl.utils;

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

    /** 事务基本操作
     *         redisTemplate.multi();
     *         redisTemplate.watch("");  WATCH 命令是一个乐观锁，可以为 Redis 事务提供 check-and-set （CAS）行为。 可以监控一个或多个键，
     *         一旦其中有一个键被修改（或删除），之后的事务就不会执行，监 控一直持续到EXEC命令。
     *          redisTemplate.unwatch();
     *         redisTemplate.exec();
     *         redisTemplate.discard();  通过调用DISCARD，客户端可以清空事务队列，并放弃执行事务， 并且客户端会从事务状态中退出
     * @param redisTemplate
     * @param redisForHash
     */

    @Autowired
    DynamicRedis(RedisTemplate<String, Object> redisTemplate,HashOperations<String,String,Object> redisForHash){
        this.redisTemplate=redisTemplate;
        this.redisForHash=redisForHash;

    }

    /**
     * 从缓存获取动态*/
    public List<Dynamic> getDynamics(){
        Object o = redisTemplate.opsForValue().get(DYNAMICS_CACHE_KEY);
        return (List<Dynamic>) o;
    }

    /**缓存多条动态
     */
    public <T> void cacheDynamics(List<T> data){
            redisTemplate.opsForValue().set(DYNAMICS_CACHE_KEY,data,60L,TimeUnit.MINUTES);
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
            redisTemplate.opsForValue().increment(key,-1);
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
    /**获取指定id动态的点赞数*/
    public int getOneDynamicZan(Long id){
        Object o = redisTemplate.opsForHash().get(DYNAMICS_ZAN_KEY, String.valueOf(id));
        if(o==null) return 0;
        return (int) o;
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

        Long count=1L;
        //没人点赞，就设置
        if(StringUtils.isEmpty(redisForHash.get(DYNAMICS_ZAN_KEY, String.valueOf(id)))){
            redisForHash.put(DYNAMICS_ZAN_KEY,String.valueOf(id),1);
        }else{  //有人点赞，递增
            count= redisForHash.increment(DYNAMICS_ZAN_KEY, String.valueOf(id), 1);
        }
        return count;
    }

}
