package com.cloud.user.service.impl;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.log;

import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.cloud.common.utils.PageUtil;
import com.cloud.model.common.Page;
import com.cloud.model.user.PermissionDTO;
import com.cloud.model.user.RoleDTO;
import com.cloud.model.user.constants.UserCenterMq;
import com.cloud.user.mapper.RoleMapper;
import com.cloud.user.mapper.RolePermissionMapper;
import com.cloud.user.mapper.UserMapper;
import com.cloud.user.mapper.UserRoleMapper;
import com.cloud.user.service.RoleService;
import com.google.common.collect.Sets;

import io.lettuce.core.models.role.RedisInstance.Role;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class RoleServiceImpl implements RoleService{
	
	@Autowired
	private RoleMapper roleMapper;
	
	@Autowired
	private UserRoleMapper userRoleMapper;
	
	@Autowired
	private RolePermissionMapper rolePermissionMapper;
	
	@Autowired
	private UserMapper userMapper;
	
	@Autowired
	private AmqpTemplate amqpTemplate;

	@Override
	public void save(RoleDTO roleDTO) {
		RoleDTO roleDTO2 = roleMapper.findByCode(roleDTO.getCode());
		if(null != roleDTO2) {
			throw new IllegalArgumentException("角色code已经存在！");
		}
		
		roleDTO.setCreateTime(new Date());
		roleDTO.setUpdateTime(new Date());
		roleMapper.save(roleDTO);
		
		log.info("新增角色：{}", JSONObject.toJSONString(roleDTO));
	}

	@Override
	public void update(RoleDTO roleDTO) {
		roleDTO.setUpdateTime(new Date());
		roleMapper.update(roleDTO);
		
		log.info("修改角色：{}", JSONObject.toJSONString(roleDTO));
	}

	@Override
	public void deleteRole(Long id) {
		RoleDTO roleDTO = roleMapper.findById(id);
		
		roleMapper.delete(id);
		
		rolePermissionMapper.deleteRolePermission(id, null);
		userRoleMapper.deleteUserRole(null, id);
		log.info("删除角色：{}", JSONObject.toJSONString(roleDTO));
		
		amqpTemplate.convertAndSend(UserCenterMq.MQ_EXCHANGE_USER, UserCenterMq.ROUTING_KEY_ROLE_DELETE, id);
	}

	@Override
	public void setPermissionToRole(Long id, Set<Long> permissionIds) {
		RoleDTO roleDTO = roleMapper.findById(id);
		if(null == roleDTO) {
			throw new IllegalArgumentException("角色不存在！");
		}
		
		// 查出角色以前的权限
		
		Set<Long> oldPermissions = rolePermissionMapper.findPermissionsByRoleIds(Sets.newHashSet(id)).stream()
				.map(p -> p.getId()).collect(Collectors.toSet());
		
		// 需要添加的权限
		Collection<Long> addPermissions = CollectionUtils.subtract(permissionIds, oldPermissions);
		
		if(!org.apache.commons.collections.CollectionUtils.isEmpty(addPermissions)) {
			addPermissions.forEach(permissionId -> {
				rolePermissionMapper.saveRolePermission(id, permissionId);
			});
		}
		
		
		// 需要删除的权限
		Collection<Long> deletePermissionIds = CollectionUtils.subtract(oldPermissions, permissionIds);
		if(!org.apache.commons.collections.CollectionUtils.isEmpty(deletePermissionIds)) {
			deletePermissionIds.forEach(permissionId -> {
				rolePermissionMapper.deleteRolePermission(id, permissionId);
			});
		}
		
		log.info("给角色：{}，分配权限：{}", id, JSONObject.toJSONString(permissionIds));
	}

	@Override
	public RoleDTO findById(Long id) {
		return roleMapper.findById(id);
	}

	@Override
	public Page<RoleDTO> findRoles(Map<String, Object> params) {
		int total = roleMapper.count(params);
		List<RoleDTO> list = Collections.emptyList();
		if (total > 0) {
			PageUtil.pageParamConver(params, false);

			list = roleMapper.findData(params);
		}
		return new Page<>(total, list);
	}

	@Override
	public Set<PermissionDTO> findPermissionsByRoleId(Long roleId) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
