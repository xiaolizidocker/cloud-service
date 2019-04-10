package com.cloud.user.service;

import java.util.Map;
import java.util.Set;

import com.cloud.model.common.Page;
import com.cloud.model.user.PermissionDTO;
import com.cloud.model.user.RoleDTO;

public interface RoleService {
	
	public void save(RoleDTO roleDTO);

	public void update(RoleDTO roleDTO);

	public void deleteRole(Long id);

	public void setPermissionToRole(Long id, Set<Long> permissionIds);

	public RoleDTO findById(Long id);

	public Page<RoleDTO> findRoles(Map<String, Object> params);

	public Set<PermissionDTO> findPermissionsByRoleId(Long roleId);

}
