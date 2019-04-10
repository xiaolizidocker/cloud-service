package com.cloud.user.service;

import java.util.Set;

import com.cloud.model.user.RoleDTO;
import com.cloud.user.entity.SysRole;

public interface UserRoleService {
	
	public Set<RoleDTO> findRolesByUserId(Long userId);

}
