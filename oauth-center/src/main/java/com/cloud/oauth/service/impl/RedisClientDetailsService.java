package com.cloud.oauth.service.impl;

import java.util.List;

import javax.sql.DataSource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.oauth2.common.exceptions.InvalidClientException;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.NoSuchClientException;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class RedisClientDetailsService extends JdbcClientDetailsService{

	@Autowired
	private StringRedisTemplate stringRedisTemplate;
	
	 /**
     * 缓存client的redis key，这里是hash结构存储
     */
    private static final String CACHE_CLIENT_KEY = "client_details";
	
	public RedisClientDetailsService(DataSource dataSource) {
		super(dataSource);
	}

	@Override
	public ClientDetails loadClientByClientId(String clientId) throws InvalidClientException {
		ClientDetails clientDetails = null;
		
		String value = (String) stringRedisTemplate.boundHashOps(CACHE_CLIENT_KEY).get(clientId);
		if(StringUtils.isBlank(value)) {
			clientDetails = cacheAndGetClient(clientId);
		}else {
			clientDetails = JSONObject.parseObject(value, BaseClientDetails.class);
		}
		return clientDetails;
	}

	@Override
	public void updateClientDetails(ClientDetails clientDetails) throws NoSuchClientException {
		super.updateClientDetails(clientDetails);
		cacheAndGetClient(clientDetails.getClientId());
	}

	@Override
	public void updateClientSecret(String clientId, String secret) throws NoSuchClientException {
		super.updateClientSecret(clientId, secret);
		cacheAndGetClient(clientId);
	}

	@Override
	public void removeClientDetails(String clientId) throws NoSuchClientException {
		super.removeClientDetails(clientId);
		removeRedisCache(clientId);
		
	}
	
	private ClientDetails cacheAndGetClient(String clientId) {
		ClientDetails clientDetails = super.loadClientByClientId(clientId);
		if(null != clientDetails) {
			stringRedisTemplate.boundHashOps(CACHE_CLIENT_KEY).put(clientId, JSONObject.toJSONString(clientDetails));
			log.info("缓存clientId：{},{}", clientId, clientDetails);
		}
		return clientDetails;
	}
	
	private void removeRedisCache(String clientId) {
		stringRedisTemplate.boundHashOps(CACHE_CLIENT_KEY).delete(clientId);
	}
	
	 /**
     * 将oauth_client_details全表刷入redis
     */
	public void loadAllClientIdToCache() {
		if(stringRedisTemplate.hasKey(CACHE_CLIENT_KEY) == Boolean.TRUE) {
			return;
		}
		log.info("将oauth_client_details全表刷入redis");
		List<ClientDetails> list = super.listClientDetails();
		if(CollectionUtils.isEmpty(list)) {
			log.error("oauth_client_details表数据为空，请检查");
            return;
		}
		list.parallelStream().forEach(clientDetail ->{
			stringRedisTemplate.boundHashOps(CACHE_CLIENT_KEY).put(clientDetail.getClientId(), JSONObject.toJSONString(clientDetail));
		});
	}
}
