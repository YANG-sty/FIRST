package com.gree.redisController;

import com.gree.config.redis.RedisUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.websocket.server.PathParam;
import java.text.MessageFormat;

/**
 * redis test interface
 */
@RestController
@RequestMapping("/api/redis")
@Api(value = "redisTest")
public class RedisTestController {

    @Resource(name = "secondMasterRedisTemplate")
    private RedisTemplate<String, Object> secondMasterRedisTemplate;

    @Resource(name = "defaultRedisTemplate")
    private RedisTemplate<String, Object> defaultRedisTemplate;

    @ApiOperation(value = "default存数据")
    @GetMapping("/putMaster")
    public String putMaster(@PathParam("value") String value) {
        int i0 = RandomUtils.nextInt(10, 100);
        int i1 = RandomUtils.nextInt(10, 100);
        RedisUtil redisUtil = new RedisUtil(defaultRedisTemplate);
        String key = MessageFormat.format("yang_zzu:first:key{0}_{1}", i0, i1);
        redisUtil.set(key, value, 300);
        return key;
    }

    @ApiOperation(value = "default取数据")
    @GetMapping("/getMaster")
    public String getMaster(@PathParam("key") String key) {
        return new RedisUtil(defaultRedisTemplate).get(key);
    }


    @ApiOperation(value = "second存数据")
    @GetMapping("/putSecond")
    public String putSecond(@PathParam("value") String value) {
        int i0 = RandomUtils.nextInt(10, 100);
        int i1 = RandomUtils.nextInt(10, 100);
        RedisUtil redisUtil = new RedisUtil(secondMasterRedisTemplate);
        String key = MessageFormat.format("yang_zzu:first:key{0}_{1}", i0, i1);
        redisUtil.set(key, value, 300);
        return key;
    }

    @ApiOperation(value = "second取数据")
    @GetMapping("/getSecond")
    public String getSecond(@PathParam("key") String key) {
        return new RedisUtil(secondMasterRedisTemplate).get(key);
    }


}
