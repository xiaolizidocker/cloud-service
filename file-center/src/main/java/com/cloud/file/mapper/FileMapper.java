package com.cloud.file.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.cloud.model.file.FileInfoDTO;

@Mapper
public interface FileMapper {
	
	@Select("select * from sys_file_info where id = #{id}")
	public FileInfoDTO getById(String id);
	
	
	@Insert("insert into sys_file_info(id, name, is_img, content_type, size, path, url, source, create_time) "
			+ "values(#{fileInfoDTO.id}, #{fileInfoDTO.name}, #{fileInfoDTO.isImg}, #{fileInfoDTO.contentType}, #{fileInfoDTO.size}, #{fileInfoDTO.path}, "
			+ "#{fileInfoDTO.url}, #{fileInfoDTO.source}, #{fileInfoDTO.createTime})")
	public int save(@Param("fileInfoDTO") FileInfoDTO fileInfoDTO);
	
	@Delete("delete from sys_file_info where id = #{id}")
	public int delete(String id);

	public int count(Map<String, Object> params);

	public List<FileInfoDTO> findData(Map<String, Object> params);
	
}
