package com.risk.riskmanage.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import javax.annotation.Resource;
import java.util.concurrent.ThreadPoolExecutor;

@Configuration
public class ConfigurationContainor {

    @Resource
    private ConfigHolder configHolder;

    @Bean(name = "threadPoolTaskExecutor")
    ThreadPoolTaskExecutor threadPoolTaskExecutor(){
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(500);
        executor.setMaxPoolSize(1000);
        executor.setQueueCapacity(200);
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.DiscardPolicy());
        return executor;
    }

    @Bean(name = "jedisPool")
    public JedisPool jedisPool(){
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(configHolder.getRedisMaxTotal());
        config.setMaxIdle(configHolder.getRedisMaxIdle());
        config.setMaxWaitMillis(configHolder.getRedisMaxWait());
        config.setTestOnBorrow(true);
//        config.setTestOnReturn(true);

        JedisPool pool = new JedisPool(config,
                configHolder.getRedisHost(),
                configHolder.getRedisPort(),
                configHolder.getRedisTimeout(),
                configHolder.getRedisPwd(),
                configHolder.getRedisDb());
        return pool;
    }

}
