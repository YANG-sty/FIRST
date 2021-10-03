package com.gree.config.redis;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

import java.time.Duration;

@Configuration
public class DefaultRedisConfig extends RedisConfig {

    @Value("${spring.redis.host}")
    private String host;

    @Value("${spring.redis.port}")
    private int port;

    @Value("${spring.redis.password}")
    private String password;

    @Value("${spring.redis.db-index}")
    private Integer dbIndex;

    @Primary
    @Bean(name = "defaultRedisTemplate")
    public RedisTemplate<String, Object> defaultRedisTemplate() {
        RedisTemplate<String, Object> template = new RedisTemplate();
        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration(host, port);
        redisStandaloneConfiguration.setPassword(RedisPassword.of(password));
        redisStandaloneConfiguration.setDatabase(dbIndex);
        JedisClientConfiguration jedisClientConfiguration = JedisClientConfiguration.builder().usePooling().
                poolConfig(jedisPoolConfig()).and().readTimeout(Duration.ofMillis(timeOut)).build();
        template.setConnectionFactory(new JedisConnectionFactory(redisStandaloneConfiguration, jedisClientConfiguration));
        setSerializer(template);
        template.afterPropertiesSet();
        return template;
    }
}
