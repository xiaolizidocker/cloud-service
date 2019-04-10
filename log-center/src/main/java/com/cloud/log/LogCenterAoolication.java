package com.cloud.log;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

import com.ctrip.framework.apollo.spring.annotation.EnableApolloConfig;

/**
 * 日志中心
 *
 */
@EnableApolloConfig
@EnableDiscoveryClient
@SpringBootApplication
public class LogCenterAoolication 
{
    public static void main( String[] args )
    {
        SpringApplication.run(LogCenterAoolication.class, args);
    }
}
