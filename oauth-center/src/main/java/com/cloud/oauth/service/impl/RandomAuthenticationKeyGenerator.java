package com.cloud.oauth.service.impl;

import java.util.UUID;

import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.AuthenticationKeyGenerator;

public class RandomAuthenticationKeyGenerator implements AuthenticationKeyGenerator{

	@Override
	public String extractKey(OAuth2Authentication arg0) {
		return UUID.randomUUID().toString();
	}

}
