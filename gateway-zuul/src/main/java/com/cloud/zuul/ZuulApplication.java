package com.cloud.zuul;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.cloud.openfeign.EnableFeignClients;

import com.ctrip.framework.apollo.spring.annotation.EnableApolloConfig;

@EnableDiscoveryClient
@EnableFeignClients
@EnableZuulProxy
@SpringBootApplication
@EnableApolloConfig
public class ZuulApplication 
{
	public static void main(String[] args) {
		SpringApplication.run(ZuulApplication.class, args);
	}
}
