package com.cloud.backend.service;

import java.util.Map;

import com.cloud.backend.entity.BlackIP;
import com.cloud.model.common.Page;

public interface BlackIPService {
	
	public void save(BlackIP blackIP);

	public void delete(String ip);

	public Page<BlackIP> findBlackIPs(Map<String, Object> params);

}
