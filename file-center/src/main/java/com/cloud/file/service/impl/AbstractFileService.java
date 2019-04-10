package com.cloud.file.service.impl;

import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONObject;
import com.cloud.file.mapper.FileMapper;
import com.cloud.file.service.FileService;
import com.cloud.file.utils.FileUtil;
import com.cloud.model.file.FileInfoDTO;
import com.cloud.model.file.FileSource;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class AbstractFileService implements FileService{
	
	protected abstract FileMapper getFileMapper();
	
	/**
	 * 文件来源
	 * 
	 * @return
	 */
	protected abstract FileSource fileSource();
	
	
	
	@Override
	public FileInfoDTO upload(MultipartFile file) throws Exception {
		FileInfoDTO fileInfoDTO = FileUtil.getFileInfo(file);
		
		FileInfoDTO olInfoDTO = getFileMapper().getById(fileInfoDTO.getId());
		
		if(null != olInfoDTO) {
			return olInfoDTO;
		}
		
		if (!fileInfoDTO.getName().contains(".")) {
			throw new IllegalArgumentException("缺少后缀名");
		}

		uploadFile(file, fileInfoDTO);

		fileInfoDTO.setSource(fileSource().name());// 设置文件来源
		getFileMapper().save(fileInfoDTO);// 将文件信息保存到数据库

		log.info("上传文件：{}", fileInfoDTO);

		return fileInfoDTO;
		
	}
	
	

	@Override
	public void delete(FileInfoDTO fileInfoDTO) {
		delete(fileInfoDTO);
		getFileMapper().delete(fileInfoDTO.getId());
		log.info("删除文件：{}", JSONObject.toJSONString(fileInfoDTO));
	}

	/**
	 * 删除文件资源
	 * 
	 * @param fileInfo
	 * @return
	 */
	protected abstract boolean deleteFile(FileInfoDTO fileInfoDTO);

	/**
	 * 上传文件
	 * 
	 * @param file
	 * @param fileInfo
	 */
	protected abstract void uploadFile(MultipartFile file, FileInfoDTO fileInfoDTO) throws Exception;
}
