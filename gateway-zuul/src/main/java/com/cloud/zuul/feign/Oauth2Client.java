package com.cloud.zuul.feign;

import java.util.Map;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("OAUTH-CENTER")
public interface Oauth2Client {
	
	@PostMapping(path = "/oauth/token")
    public Map<String, Object> postAccessToken(@RequestParam Map<String, String> parameters);
	
	@DeleteMapping(path = "/remove_token")
	public void removeToken(@RequestParam("access_token") String access_token);
}
