package com.cloud.user.service;

import java.util.Map;
import java.util.Set;

import com.cloud.model.common.Page;
import com.cloud.model.user.LoginAppUser;
import com.cloud.model.user.RoleDTO;
import com.cloud.model.user.UserDTO;

public interface UserService {

	LoginAppUser findByUsername(String username);
	
	LoginAppUser findById(Long id);
	
	Page<UserDTO> findUsers(Map<String, Object> params);
	
	void addSysUser(UserDTO userDTO);
	
	public void updateUser(UserDTO userDTO);
	
	public void setRoleToUser(Long id, Set<Long> roleIds);
	
	public void updatePassword(Long id, String oldPassword, String newPassword);
	
	public Set<RoleDTO> findRolesByUserId(Long userId);
	
	public void bindingPhone(Long userId, String phone);
}
