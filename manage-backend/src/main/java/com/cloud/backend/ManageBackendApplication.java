package com.cloud.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

import com.alibaba.dubbo.spring.boot.annotation.EnableDubboConfiguration;
import com.ctrip.framework.apollo.spring.annotation.EnableApolloConfig;

@EnableDiscoveryClient
@SpringBootApplication
@EnableDubboConfiguration
@EnableApolloConfig
public class ManageBackendApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(ManageBackendApplication.class, args);
	}
}
