package com.cloud.user.service.impl;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cloud.model.user.RoleDTO;
import com.cloud.user.entity.SysRole;
import com.cloud.user.mapper.UserRoleMapper;
import com.cloud.user.service.UserRoleService;

@Service
public class UserRoleServiceImpl implements UserRoleService{
	
	@Autowired
	private UserRoleMapper userRoleMapper;

	@Override
	public Set<RoleDTO> findRolesByUserId(Long userId) {
		return userRoleMapper.findRolesByUserId(userId);
	}

}
