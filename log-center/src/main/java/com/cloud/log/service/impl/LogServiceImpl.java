package com.cloud.log.service.impl;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cloud.common.utils.PageUtil;
import com.cloud.log.mapper.LogMapper;
import com.cloud.log.service.LogService;
import com.cloud.model.common.Page;
import com.cloud.model.log.LogDTO;

@Service
public class LogServiceImpl implements LogService{
	
	@Autowired
	private LogMapper logMapper;

	@Override
	public void save(LogDTO logDTO) {
		if(null == logDTO) {
			return;
		}
		if(null == logDTO.getCreateTime()) {
			logDTO.setCreateTime(new Date());
		}
		if(null == logDTO.getFlag()) {
			logDTO.setFlag(1);
		}
		logMapper.save(logDTO);
	}

	@Override
	public Page<LogDTO> findLogs(Map<String, Object> params) {
		int total = logMapper.count(params);
		List<LogDTO> list = Collections.emptyList();
		if(total > 0) {
			PageUtil.pageParamConver(params, true);
			list = logMapper.findData(params);
		}
		return new Page<>(total, list);
	}

}
