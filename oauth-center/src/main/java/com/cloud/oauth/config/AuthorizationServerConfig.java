package com.cloud.oauth.config;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

import com.cloud.model.user.LoginAppUser;
import com.cloud.oauth.service.impl.RandomAuthenticationKeyGenerator;
import com.cloud.oauth.service.impl.RedisAuthorizationCodeServices;
import com.cloud.oauth.service.impl.RedisClientDetailsService;

/**
 * 配置授权服务器
 * @author zhouyu
 *
 */
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter{
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private RedisConnectionFactory redisConnectionFactory;
	
	@Autowired
	private RedisAuthorizationCodeServices redisAuthorizationCodeServices;
	
	@Autowired
	private RedisClientDetailsService redisClientDetailsService;
	
	@Bean
	public MyRedisTokenStore tokenStore() {
		MyRedisTokenStore redisTokenStore = new MyRedisTokenStore(redisConnectionFactory);
		redisTokenStore.setAuthenticationKeyGenerator(new RandomAuthenticationKeyGenerator());
		return redisTokenStore;
	}

	@Override
	public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
		security.allowFormAuthenticationForClients();
	}

	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		clients.withClientDetails(redisClientDetailsService);
		redisClientDetailsService.loadAllClientIdToCache();
	}

	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		endpoints.authenticationManager(this.authenticationManager);
		endpoints.tokenStore(tokenStore());
		endpoints.authorizationCodeServices(redisAuthorizationCodeServices);
		
		endpoints.tokenEnhancer((accessToken, authentication) -> {
			addLoginUserInfo(accessToken, authentication);
			return accessToken;
		});
		
		
	}
	
	private void addLoginUserInfo(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
		if(accessToken instanceof DefaultOAuth2AccessToken) {
			DefaultOAuth2AccessToken defaultOAuth2AccessToken = (DefaultOAuth2AccessToken) accessToken;
			Authentication userAuthentication = authentication.getUserAuthentication();
			Object principal = userAuthentication.getPrincipal();
			if(principal instanceof LoginAppUser) {
				LoginAppUser loginAppUser = (LoginAppUser) principal;
				// 将登陆者信息写会，方便后面获取
				Map<String, Object> map = new HashMap<>(defaultOAuth2AccessToken.getAdditionalInformation());
				map.put("loginUser", loginAppUser);
				defaultOAuth2AccessToken.setAdditionalInformation(map);
			}
			
		}
		
		
	}
	
}
