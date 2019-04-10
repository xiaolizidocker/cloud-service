package com.cloud.oauth.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.cloud.model.user.LoginAppUser;

@FeignClient("USER-CENTER")
public interface UserClient{
	@GetMapping(value = "/users-anon/internal", params = "username")
    public LoginAppUser findByUsername(@RequestParam("username") String username);
}
