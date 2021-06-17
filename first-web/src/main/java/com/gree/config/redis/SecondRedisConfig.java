package com.gree.config.redis;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

import java.time.Duration;


@Configuration
public class SecondRedisConfig extends RedisConfig {

    /**
     * second 主库
     */
    @Value("${spring.second.master.host}")
    private String masterHost;

    @Value("${spring.second.master.port}")
    private int masterPort;

    @Value("${spring.second.master.password}")
    private String masterPassword;

    @Value("${spring.second.master.db-index}")
    protected int dbMasterIndex;

    /**
     * second 从库
     */
    @Value("${spring.second.slave.host}")
    private String slaveHost;

    @Value("${spring.second.slave.port}")
    private int slavePort;

    @Value("${spring.second.slave.password}")
    private String slavePassword;

    @Value("${spring.second.slave.db-index}")
    protected int dbSlaveIndex;

    /**
     * 配置secondMasterRedisTemplate
     */
    @Bean(name = "secondMasterRedisTemplate")
    public RedisTemplate secondMasterRedisTemplate() {
        RedisTemplate<String, Object> template = new RedisTemplate();
        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration(masterHost, masterPort);
        redisStandaloneConfiguration.setPassword(RedisPassword.of(masterPassword));
        redisStandaloneConfiguration.setDatabase(dbMasterIndex);
        JedisClientConfiguration jedisClientConfiguration = JedisClientConfiguration.builder().usePooling().
                poolConfig(jedisPoolConfig()).and().readTimeout(Duration.ofMillis(timeOut)).build();
        template.setConnectionFactory(new JedisConnectionFactory(redisStandaloneConfiguration, jedisClientConfiguration));
        setSerializer(template);
        template.afterPropertiesSet();
        return template;
    }

    /**
     * 配置secondSlaveRedisTemplate
     */
    @Bean(name = "secondSlaveRedisTemplate")
    public RedisTemplate<String, Object> secondSlaveRedisTemplate() {
        RedisTemplate<String, Object> template = new RedisTemplate();
        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration(slaveHost, slavePort);
        redisStandaloneConfiguration.setPassword(RedisPassword.of(slavePassword));
        redisStandaloneConfiguration.setDatabase(dbSlaveIndex);
        JedisClientConfiguration jedisClientConfiguration = JedisClientConfiguration.builder().usePooling().
                poolConfig(jedisPoolConfig()).and().readTimeout(Duration.ofMillis(timeOut)).build();
        template.setConnectionFactory(new JedisConnectionFactory(redisStandaloneConfiguration, jedisClientConfiguration));
        setSerializer(template);
        template.afterPropertiesSet();
        return template;
    }

}
