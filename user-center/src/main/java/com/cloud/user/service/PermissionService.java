package com.cloud.user.service;

import java.util.Map;
import java.util.Set;

import com.cloud.model.common.Page;
import com.cloud.model.user.PermissionDTO;
import com.cloud.user.entity.SysPermission;

public interface PermissionService {
	
	public Set<SysPermission> findByRoleIds(Set<Long> roleIds);

	public void save(PermissionDTO permissionDTO);

	public void update(PermissionDTO permissionDTO);

	public void delete(Long id);

	public Page<PermissionDTO> findPermissions(Map<String, Object> params);
	
}
