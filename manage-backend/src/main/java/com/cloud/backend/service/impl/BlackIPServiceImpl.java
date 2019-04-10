package com.cloud.backend.service.impl;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cloud.backend.entity.BlackIP;
import com.cloud.backend.mapper.BlackIPMapper;
import com.cloud.backend.service.BlackIPService;
import com.cloud.common.utils.PageUtil;
import com.cloud.model.common.Page;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class BlackIPServiceImpl implements BlackIPService{

	@Autowired
	private BlackIPMapper blackIpMapper;
	
	@Override
	public void save(BlackIP blackIP) {
		BlackIP ip = blackIpMapper.findByIp(blackIP.getIp());
		if (ip != null) {
			throw new IllegalArgumentException(blackIP.getIp() + "已存在");
		}

		blackIpMapper.save(blackIP);
		log.info("添加黑名单ip:{}", blackIP);
	}

	@Override
	public void delete(String ip) {
		int n = blackIpMapper.delete(ip);
		if (n > 0) {
			log.info("删除黑名单ip:{}", ip);
		}
	}

	@Override
	public Page<BlackIP> findBlackIPs(Map<String, Object> params) {
		int total = blackIpMapper.count(params);
		List<BlackIP> list = Collections.emptyList();
		if (total > 0) {
			PageUtil.pageParamConver(params, false);

			list = blackIpMapper.findData(params);
		}
		return new Page<>(total, list);
	}

}
