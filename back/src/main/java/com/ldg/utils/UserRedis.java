package com.ldg.utils;

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
        if (!StringUtils.isEmpty(userInfo)) {
            String key = USERINFO_CACHE_KEY + userInfo.getUser().getId();
            redisTemplate.opsForValue().setIfPresent(key, userInfo, 60L, TimeUnit.MINUTES);
        }
    }


}
