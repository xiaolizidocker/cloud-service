package com.cloud.common.utils;

import java.util.Map;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;

import com.alibaba.fastjson.JSONObject;
import com.cloud.model.user.LoginAppUser;

public class AppUserUtil {
	
	@SuppressWarnings("rawtypes")
	public static LoginAppUser getLoginAppUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if(authentication instanceof OAuth2Authentication) {
			OAuth2Authentication oAuth2Authentication = (OAuth2Authentication) authentication;
			authentication = oAuth2Authentication.getUserAuthentication();
			
			if(authentication instanceof UsernamePasswordAuthenticationToken) {
				UsernamePasswordAuthenticationToken authenticationToken = (UsernamePasswordAuthenticationToken) authentication;
				Object pricipal = authentication.getPrincipal();
				if(pricipal instanceof LoginAppUser) {
					return (LoginAppUser) pricipal;
				}
				Map map = (Map) authenticationToken.getDetails();
				map = (Map) map.get("principal");
				return JSONObject.parseObject(JSONObject.toJSONString(map), LoginAppUser.class);
			}
		}
		return null;
	}

}
