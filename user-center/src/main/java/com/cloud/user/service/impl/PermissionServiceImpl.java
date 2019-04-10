package com.cloud.user.service.impl;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.log;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.cloud.common.utils.PageUtil;
import com.cloud.model.common.Page;
import com.cloud.model.user.PermissionDTO;
import com.cloud.user.entity.SysPermission;
import com.cloud.user.mapper.PermissionMapper;
import com.cloud.user.mapper.RolePermissionMapper;
import com.cloud.user.service.PermissionService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class PermissionServiceImpl implements PermissionService{
	
	@Autowired
	private PermissionMapper permissionMapper;
	
	@Autowired
	private RolePermissionMapper rolePermissionMapper;

	@Override
	public Set<SysPermission> findByRoleIds(Set<Long> roleIds) {
		return permissionMapper.findByRoleIds(roleIds);
	}

	@Override
	public void save(PermissionDTO permissionDTO) {
		PermissionDTO pDto = permissionMapper.findByPermission(permissionDTO.getPermission());
		if(null != pDto) {
			throw new IllegalArgumentException("权限标识已存在！");
		}
		
		permissionDTO.setCreateTime(new Date());
		permissionDTO.setUpdateTime(new Date());
		
		permissionMapper.save(permissionDTO);
		log.info("保存权限标识：{}", JSONObject.toJSONString(permissionDTO));
	}

	@Override
	public void update(PermissionDTO permissionDTO) {
		permissionDTO.setUpdateTime(new Date());
		
		permissionMapper.update(permissionDTO);
		
		log.info("修改权限：{}", JSONObject.toJSONString(permissionDTO));
		
	}

	@Override
	public void delete(Long id) {
		PermissionDTO permissionDTO = permissionMapper.findById(id);
		if(null == permissionDTO) {
			throw new IllegalArgumentException("权限标识不存在！");
		}
		
		permissionMapper.delete(id);
		
		rolePermissionMapper.deleteRolePermission(null, id);
		
		log.info("删除标识权限：{}", JSONObject.toJSONString(permissionDTO));
		
	}

	@Override
	public Page<PermissionDTO> findPermissions(Map<String, Object> params) {
		int total = permissionMapper.count(params);
		List<PermissionDTO> list = Collections.emptyList();
		if (total > 0) {
			PageUtil.pageParamConver(params, false);

			list = permissionMapper.findData(params);
		}
		return new Page<>(total, list);
	}

}
