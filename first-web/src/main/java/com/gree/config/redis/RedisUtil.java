package com.gree.config.redis;

import io.lettuce.core.RedisException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.concurrent.TimeUnit;

@Slf4j
public class RedisUtil {

    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 在使用redis的时候，首先传递是哪一个 redisTemplate
     */
    public RedisUtil(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public <T> void set(String key, T data, long time) {
        try {
            redisTemplate.opsForValue().set(key, data, time, TimeUnit.SECONDS);
        } catch (Throwable t) {
            throw new RedisException("redis set 操作异常", t);
        }
    }

    public <T> T get(String key) {
        T data = null;
        try {
            data = (T) redisTemplate.opsForValue().get(key);
        } catch (Throwable t) {
            throw new RedisException("redis get 操作异常", t);
        }
        return data;
    }

    public <T> T hget(String key, String item) {
        T data = null;
        try {
            log.info("key:{}",key);
            data = (T) redisTemplate.opsForHash().get(key, item);
            log.info("data:{}",data);
        } catch (Throwable t) {
            throw new RedisException("redis hget 操作异常", t);
        }
        return data;
    }

}
