package com.cloud.notification;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

import com.alibaba.dubbo.spring.boot.annotation.EnableDubboConfiguration;
import com.ctrip.framework.apollo.spring.annotation.EnableApolloConfig;

@SpringBootApplication
@EnableDiscoveryClient
@EnableApolloConfig
@EnableDubboConfiguration
public class NotificationCenterApplication 
{
    public static void main( String[] args )
    {
        SpringApplication.run(NotificationCenterApplication.class, args);
    }
}
