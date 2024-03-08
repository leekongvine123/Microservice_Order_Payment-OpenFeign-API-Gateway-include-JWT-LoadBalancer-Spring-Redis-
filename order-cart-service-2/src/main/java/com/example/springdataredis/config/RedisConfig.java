package com.example.springdataredis.config;


import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;

@Configuration
@EnableRedisRepositories
@Component
public class RedisConfig {

public JedisConnectionFactory connectionFactory(){
    RedisStandaloneConfiguration configuration = new RedisStandaloneConfiguration();
    configuration.setHostName("localhost");
    configuration.setPort(6379);
    return new JedisConnectionFactory(configuration);

}

public RedisTemplate<String,Object> redisTemplate(){
    RedisTemplate<String,Object> template = new RedisTemplate<>();
    template.setConnectionFactory(connectionFactory());
    template.setKeySerializer(new StringRedisSerializer());
    template.setHashKeySerializer(new JdkSerializationRedisSerializer());
    template.setValueSerializer(new JdkSerializationRedisSerializer());
    template.setEnableTransactionSupport(true);
    template.afterPropertiesSet();
    return template;


}
}
