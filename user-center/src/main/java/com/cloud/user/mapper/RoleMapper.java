package com.cloud.user.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import com.cloud.model.user.RoleDTO;

@Mapper
public interface RoleMapper {
	
	@Options(useGeneratedKeys = true, keyProperty = "id")
	@Insert("insert into sys_role(code, name, create_time, update_time) values(#{code}, #{name}, #{createTime}, #{createTime})")
	public int save(RoleDTO roleDTO);

	@Update("update sys_role t set t.name = #{name} ,t.updateTime = #{updateTime} where t.id = #{id}")
	public int update(RoleDTO roleDTO);

	@Select("select * from sys_role t where t.id = #{id}")
	public RoleDTO findById(Long id);

	@Select("select * from sys_role t where t.code = #{code}")
	public RoleDTO findByCode(String code);

	@Delete("delete from sys_role where id = #{id}")
	public int delete(Long id);

	public int count(Map<String, Object> params);

	public List<RoleDTO> findData(Map<String, Object> params);

}
