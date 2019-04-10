package com.cloud.zuul.feign;

import java.util.Map;
import java.util.Set;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("manage-backend")
public interface BackendClient {
	
	@GetMapping("/backend-anon/internal/blackIPs")
	public Set<String> findAllBlackIPs(@RequestParam("params") Map<String, Object> params);

}
