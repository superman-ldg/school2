package com.ldg.service.impl.utils;

import com.ldg.pojo.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.concurrent.TimeUnit;

/**
 * @author 梁登光
 */
@Component
public class UserRedis {

    private static final String USERINFO_CACHE_KEY="userInfo:";

    private RedisTemplate<String,Object> redisTemplate;

    @Autowired
    UserRedis(RedisTemplate<String, Object> redisTemplate){
        this.redisTemplate=redisTemplate;
    }

    /**
     * 缓存个人信息*/
    public <D,G> void cacheUserInfo(UserInfo<D,G> userInfo) {
            String key = USERINFO_CACHE_KEY + userInfo.getUser().getIdCard();
            redisTemplate.opsForValue().set(key, userInfo, 60L, TimeUnit.MINUTES);
    }

    /**
     * 从缓存获取个人信息
     * */
    public UserInfo getUserInfo(Long uid) {
        String key = USERINFO_CACHE_KEY +uid;
        return (UserInfo) redisTemplate.opsForValue().get(key);
    }

    /**
     * 从缓存获取个人信息
     * */
    public void deleteUserInfo(Long uid) {
        String key = USERINFO_CACHE_KEY +uid;
         redisTemplate.delete(key);
    }


}
