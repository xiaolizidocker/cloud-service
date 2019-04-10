package com.cloud.file.service;

import org.springframework.web.multipart.MultipartFile;

import com.cloud.model.file.FileInfoDTO;

public interface FileService {
	
	/**
	 * 上传文件
	 *
	 * @param file
	 * @return
	 * @throws Exception
	 */
	public FileInfoDTO upload(MultipartFile file) throws Exception;

	/**
	 * 删除文件
	 *
	 * @param fileInfoDTO
	 */
	public void delete(FileInfoDTO fileInfoDTO);

}
