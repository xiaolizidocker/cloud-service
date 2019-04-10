package com.cloud.file.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.aliyun.oss.OSSClient;
import com.cloud.file.mapper.FileMapper;
import com.cloud.model.file.FileInfoDTO;
import com.cloud.model.file.FileSource;


/**
 * 阿里云存储文件
 * @author zhouyu
 *
 */
//@Service("aliyunFileServiceImpl")
public class AliyunFileServiceImpl extends AbstractFileService{

	@Autowired
	private FileMapper fileMapper;
	
	@Override
	protected FileMapper getFileMapper() {
		return fileMapper;
	}

	@Override
	protected FileSource fileSource() {
		return FileSource.ALIYUN;
	}
	
	@Autowired
	private OSSClient ossClient;
	
	
	@Value("${file.aliyun.bucketName}")
	private String bucketName;
	@Value("${file.aliyun.domain}")
	private String domain;

	@Override
	protected boolean deleteFile(FileInfoDTO fileInfoDTO) {
		ossClient.deleteObject(bucketName, fileInfoDTO.getName());
		return true;
	}

	@Override
	protected void uploadFile(MultipartFile file, FileInfoDTO fileInfoDTO) throws Exception {
		ossClient.putObject(bucketName, fileInfoDTO.getName(), file.getInputStream());
		fileInfoDTO.setUrl(domain + "/" + fileInfoDTO.getName());
	}

}
