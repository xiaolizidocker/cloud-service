package com.cloud.zuul.controller;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.util.OAuth2Utils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.cloud.zuul.constants.SystemClientInfo;
import com.cloud.zuul.feign.Oauth2Client;

import lombok.extern.slf4j.Slf4j;

@RestController
public class TokenController {
	
	@Autowired
	private Oauth2Client oauth2Client;
	
	/**
	 * 登录
	 * @param username
	 * @param password
	 * @return
	 */
	@PostMapping("/sys/login")
	public Map<String, Object> login(String username, String password) {
		Map<String, String> parameters = new HashMap<>();
        parameters.put(OAuth2Utils.GRANT_TYPE, "password");
        parameters.put(OAuth2Utils.CLIENT_ID, SystemClientInfo.CLIENT_ID);
        parameters.put("client_secret", SystemClientInfo.CLIENT_SECRET);
        parameters.put(OAuth2Utils.SCOPE, SystemClientInfo.CLIENT_SCOPE);
        parameters.put("username", username);
        parameters.put("password", password);

        Map<String, Object> tokenInfo = oauth2Client.postAccessToken(parameters);
        return tokenInfo;
	}
	
	/**
	 * 注销登录
	 * @param access_token
	 * @param token
	 */
	@GetMapping("/sys/logout")
	public void logout(String access_token, @RequestHeader(required = false, value = "Authorization") String token) {
		if(StringUtils.isBlank(access_token)) {
			if(StringUtils.isNotBlank(token)) {
				access_token = token.substring(OAuth2AccessToken.BEARER_TYPE.length() + 1);
			}
		}
		oauth2Client.removeToken(access_token);
	}

}
