package com.cloud.user.controller;

import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cloud.model.common.Page;
import com.cloud.model.log.LogAnnotation;
import com.cloud.model.user.PermissionDTO;
import com.cloud.model.user.RoleDTO;
import com.cloud.user.service.RoleService;

@RestController
public class RoleController {
	
	@Autowired
	private RoleService roleService;
	
	/**
	 * 管理后台添加角色
	 * 
	 * @param roleDTO
	 */
	@LogAnnotation(module = "添加角色")
	@PreAuthorize("hasAuthority('back:role:save')")
	@PostMapping("/roles")
	public RoleDTO save(@RequestBody RoleDTO roleDTO) {
		if (StringUtils.isBlank(roleDTO.getCode())) {
			throw new IllegalArgumentException("角色code不能为空");
		}
		if (StringUtils.isBlank(roleDTO.getName())) {
			roleDTO.setName(roleDTO.getCode());
		}

		roleService.save(roleDTO);

		return roleDTO;
	}
	
	
	/**
	 * 管理后台删除角色
	 * 
	 * @param id
	 */
	@LogAnnotation(module = "删除角色")
	@PreAuthorize("hasAuthority('back:role:delete')")
	@DeleteMapping("/roles/{id}")
	public void deleteRole(@PathVariable Long id) {
		roleService.deleteRole(id);
	}
	
	
	/**
	 * 管理后台修改角色
	 * 
	 * @param roleDTO
	 */
	@LogAnnotation(module = "修改角色")
	@PreAuthorize("hasAuthority('back:role:update')")
	@PutMapping("/roles")
	public RoleDTO update(@RequestBody RoleDTO roleDTO) {
		if (StringUtils.isBlank(roleDTO.getName())) {
			throw new IllegalArgumentException("角色名不能为空");
		}

		roleService.update(roleDTO);

		return roleDTO;
	}
	
	
	/**
	 * 管理后台给角色分配权限
	 *
	 * @param id            角色id
	 * @param permissionIds 权限ids
	 */
	@LogAnnotation(module = "分配权限")
	@PreAuthorize("hasAuthority('back:role:permission:set')")
	@PostMapping("/roles/{id}/permissions")
	public void setPermissionToRole(@PathVariable Long id, @RequestBody Set<Long> permissionIds) {
		roleService.setPermissionToRole(id, permissionIds);
	}

	/**
	 * 获取角色的权限
	 * 
	 * @param id
	 */
	@PreAuthorize("hasAnyAuthority('back:role:permission:set','role:permission:byroleid')")
	@GetMapping("/roles/{id}/permissions")
	public Set<PermissionDTO> findPermissionsByRoleId(@PathVariable Long id) {
		return roleService.findPermissionsByRoleId(id);
	}

	@PreAuthorize("hasAuthority('back:role:query')")
	@GetMapping("/roles/{id}")
	public RoleDTO findById(@PathVariable Long id) {
		return roleService.findById(id);
	}

	/**
	 * 搜索角色
	 * 
	 * @param params
	 */
	@PreAuthorize("hasAuthority('back:role:query')")
	@GetMapping("/roles")
	public Page<RoleDTO> findRoles(@RequestParam Map<String, Object> params) {
		return roleService.findRoles(params);
	}


}
