package com.cloud.user.controller;

import java.util.Map;

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
import com.cloud.user.service.PermissionService;

@RestController
public class PermissionController {
	
	@Autowired
	private PermissionService permissionService;
	
	/**
	 * 管理后台添加权限
	 * 
	 * @param permissionDTO
	 * @return
	 */
	@LogAnnotation(module = "添加权限")
	@PreAuthorize("hasAuthority('back:permission:save')")
	@PostMapping("/permissions")
	public PermissionDTO save(@RequestBody PermissionDTO permissionDTO) {
		if (StringUtils.isBlank(permissionDTO.getPermission())) {
			throw new IllegalArgumentException("权限标识不能为空");
		}
		if (StringUtils.isBlank(permissionDTO.getName())) {
			throw new IllegalArgumentException("权限名不能为空");
		}

		permissionService.save(permissionDTO);

		return permissionDTO;
	}
	
	
	/**
	 * 管理后台修改权限
	 * 
	 * @param permissionDTO
	 */
	@LogAnnotation(module = "修改权限")
	@PreAuthorize("hasAuthority('back:permission:update')")
	@PutMapping("/permissions")
	public PermissionDTO update(@RequestBody PermissionDTO permissionDTO) {
		if (StringUtils.isBlank(permissionDTO.getName())) {
			throw new IllegalArgumentException("权限名不能为空");
		}

		permissionService.update(permissionDTO);

		return permissionDTO;
	}

	/**
	 * 删除权限标识
	 * 
	 * @param id
	 */
	@LogAnnotation(module = "删除权限")
	@PreAuthorize("hasAuthority('back:permission:delete')")
	@DeleteMapping("/permissions/{id}")
	public void delete(@PathVariable Long id) {
		permissionService.delete(id);
	}

	/**
	 * 查询所有的权限标识
	 */
	@PreAuthorize("hasAuthority('back:permission:query')")
	@GetMapping("/permissions")
	public Page<PermissionDTO> findPermissions(@RequestParam Map<String, Object> params) {
		return permissionService.findPermissions(params);
	}

}
