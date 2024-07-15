package com.redis.common;


import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RedisConfiguration{
    @Bean
    public KBankRedisTemplate kBankRedisTemplate(ApplicationContext applicationContext){
        return new KBankRedisTemplate(applicationContext);
    }
}
