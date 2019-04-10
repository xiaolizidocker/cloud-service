package com.cloud.zuul.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.cloud.model.log.LogDTO;

@FeignClient("log-center")
public interface LogClient {
	
	@PostMapping("/logs-anon/internal")
	public void save(@RequestBody LogDTO logDTO);

}
