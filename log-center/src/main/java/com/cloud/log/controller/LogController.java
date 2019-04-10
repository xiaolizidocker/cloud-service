package com.cloud.log.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cloud.log.service.LogService;
import com.cloud.model.common.Page;
import com.cloud.model.log.LogDTO;

@RestController
public class LogController {
	
	@Autowired
	private LogService logService;
	
	@PostMapping("/logs-anon/internal")
	public void save(@RequestBody LogDTO logDTO) {
		logService.save(logDTO);
	}
	
	@PreAuthorize("hasAuthority('log:query')")
	@GetMapping("/logs")
	public Page<LogDTO> findLogs(@RequestParam Map<String, Object> params){
		return logService.findLogs(params);
	}

}
