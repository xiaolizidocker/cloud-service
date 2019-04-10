package com.cloud.backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;

import feign.RequestInterceptor;
import feign.RequestTemplate;

/**
 * 使用feign client调用其他微服务时， 将access_token放入到header的Authorization里面去。
 * @author zhouyu
 *
 */
@Configuration
public class FeignInterceptorConfig {
	
	@Bean
	public RequestInterceptor requestInterceptor() {
		
		RequestInterceptor requestInterceptor = new RequestInterceptor() {
			
			@Override
			public void apply(RequestTemplate requestTemplate) {
				Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
				if(authentication != null) {
					if(authentication instanceof OAuth2AccessToken) {
						OAuth2AuthenticationDetails details = (OAuth2AuthenticationDetails) authentication;
						String access_token = details.getTokenValue();
						requestTemplate.header("Authorization", OAuth2AccessToken.BEARER_TYPE+" "+access_token);
					}
				}
				
			}
		};
		return requestInterceptor;
	}

}
