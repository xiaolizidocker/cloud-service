package com.cloud.redis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.alibaba.dubbo.spring.boot.annotation.EnableDubboConfiguration;
import com.ctrip.framework.apollo.spring.annotation.EnableApolloConfig;

/**
 * redis操作工具
 *
 */
@EnableDubboConfiguration
@EnableApolloConfig
@SpringBootApplication
public class RedisCenterApplication 
{
    public static void main( String[] args )
    {
        SpringApplication.run(RedisCenterApplication.class, args);
    }
}
