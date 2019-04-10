package com.cloud.user.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.cloud.common.utils.PageUtil;
import com.cloud.model.common.Page;
import com.cloud.model.user.LoginAppUser;
import com.cloud.model.user.RoleDTO;
import com.cloud.model.user.UserDTO;
import com.cloud.user.constants.UserType;
import com.cloud.user.entity.SysPermission;
import com.cloud.user.entity.SysRole;
import com.cloud.user.entity.SysUser;
import com.cloud.user.mapper.UserMapper;
import com.cloud.user.mapper.UserRoleMapper;
import com.cloud.user.service.PermissionService;
import com.cloud.user.service.UserRoleService;
import com.cloud.user.service.UserService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserServiceImpl implements UserService{

	@Autowired
	private UserMapper userMapper;
	
	@Autowired
	private UserRoleMapper userRoleMapper;
	
	@Autowired
	private UserRoleService userRoleService;
	
	@Autowired
	private PermissionService permissionService;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Override
	public LoginAppUser findByUsername(String username) {
		
		SysUser sysUser = userMapper.findByUsername(username);
		if(null != sysUser) {
			LoginAppUser loginAppUser = new LoginAppUser();
			BeanUtils.copyProperties(sysUser, loginAppUser);
			
			Set<RoleDTO> roles = userRoleService.findRolesByUserId(sysUser.getId());
			
			if(!CollectionUtils.isEmpty(roles)) {
				Set<Long> roleIds = new HashSet<>();
				roles.forEach(sysRole -> {
					roleIds.add(sysRole.getId());
				});
				loginAppUser.setSysRoles(roles); //设置角色
				
				Set<SysPermission> permissions = permissionService.findByRoleIds(roleIds);
				if(!CollectionUtils.isEmpty(permissions)) {
					Set<String> permission = new HashSet<>();
					permissions.forEach(sysPermission -> {
						permission.add(sysPermission.getPermission());
					});
					loginAppUser.setPermissions(permission); //设置权限集合
				}
			}
			return loginAppUser;
		}
		return null;
	}

	@Override
	public LoginAppUser findById(Long id) {
		SysUser user = userMapper.findById(id);
		LoginAppUser loginAppUser = new LoginAppUser();
		if(null != user) {
			BeanUtils.copyProperties(user, loginAppUser);
		}
		return loginAppUser;
		
	}

	@Override
	public Page<UserDTO> findUsers(Map<String, Object> params) {
		int total = userMapper.count(params);
		List<UserDTO> result = new ArrayList<>();
		if(total > 0) {
			PageUtil.pageParamConver(params, true);
			result = userMapper.findData(params);
		}
		return new Page<>(total, result);
	}

	@Transactional
	@Override
	public void addSysUser(UserDTO userDTO) {
		String username = userDTO.getUsername();
		if(StringUtils.isBlank(username)) {
			throw new IllegalArgumentException("用户名不能为空！");
		}
		if(username.contains("|")) {
			throw new IllegalArgumentException("用户名不能包含|字符！");
		}
		if(StringUtils.isBlank(userDTO.getPassword())) {
			throw new IllegalArgumentException("密码不能为空！");
		}
		if(StringUtils.isBlank(userDTO.getNickName())) {
			userDTO.setNickName(username);
		}
		if(StringUtils.isBlank(userDTO.getType())) {
			userDTO.setType(UserType.APP.name());
		}
		SysUser user = userMapper.findByUsername(username);
		if(null != user) {
			throw new IllegalArgumentException("用户名已经存在！");
		}
		// 密码加密
		userDTO.setPassword(bCryptPasswordEncoder.encode(userDTO.getPassword()));
		userDTO.setEnabled(Boolean.TRUE);
		userDTO.setCreateTime(new Date());
		userDTO.setUpdateTime(new Date());
		userMapper.save(userDTO);
		
		log.info("添加用户：{}", JSONObject.toJSONString(userDTO));
	}

	@Override
	public void updateUser(UserDTO userDTO) {
		userDTO.setUpdateTime(new Date());
		userMapper.update(userDTO);
		log.info("修改用户：{}", JSONObject.toJSONString(userDTO));
	}

	@Override
	public void setRoleToUser(Long id, Set<Long> roleIds) {
		SysUser sysUser = userMapper.findById(id);
		
		if(null == sysUser) {
			throw new IllegalArgumentException("用户不存在！");
		}
		
		userRoleMapper.deleteUserRole(id, null);
		if(!CollectionUtils.isEmpty(roleIds)) {
			roleIds.forEach(roleId -> {
				userRoleMapper.saveUserRoles(id, roleId);
			});
		}
		log.info("修改用户：{}的角色：{}", sysUser.getUsername(), JSONObject.toJSONString(roleIds));
	}

	@Override
	public void updatePassword(Long id, String oldPassword, String newPassword) {
		SysUser sysUser = userMapper.findById(id);
		
		if(StringUtils.isNotBlank(oldPassword)) {
			if(!bCryptPasswordEncoder.matches(oldPassword, sysUser.getPassword())) {
				throw new IllegalArgumentException("旧密码错误！");
			}
		}
		
		UserDTO userDTO = new UserDTO();
		userDTO.setId(id);
		userDTO.setPassword(bCryptPasswordEncoder.encode(newPassword));
		updateUser(userDTO);
	}

	@Override
	public Set<RoleDTO> findRolesByUserId(Long userId) {
		return userRoleMapper.findRolesByUserId(userId);
	}

	@Override
	public void bindingPhone(Long userId, String phone) {
		// TODO Auto-generated method stub
		
	}

}
