package com.cloud.log.service;

import java.util.Map;

import com.cloud.model.common.Page;
import com.cloud.model.log.LogDTO;

public interface LogService {
	
	public void save(LogDTO logDTO);
	
	public Page<LogDTO> findLogs(Map<String, Object> params);

}
