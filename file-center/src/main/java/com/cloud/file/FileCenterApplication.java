package com.cloud.file;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

import com.ctrip.framework.apollo.spring.annotation.EnableApolloConfig;

/**
 * 文件中心
 *
 */
@EnableApolloConfig
@EnableDiscoveryClient
@SpringBootApplication
public class FileCenterApplication 
{
    public static void main( String[] args )
    {
        SpringApplication.run(FileCenterApplication.class, args);
    }
}
