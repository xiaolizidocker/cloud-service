package com.cloud.file.service.impl;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cloud.file.mapper.FileMapper;
import com.cloud.file.utils.FileUtil;
import com.cloud.model.file.FileInfoDTO;
import com.cloud.model.file.FileSource;

@Service("localFileServiceImpl")
public class LocalFileServiceImpl extends AbstractFileService{

	
	@Autowired
	private FileMapper fileMapper;
	
	@Value("${file.local.urlPrefix}")
	private String urlPrefix;
	/**
	 * 上传文件存储在本地的根路径
	 */
	@Value("${file.local.path}")
	private String localFilePath;
	
	@Override
	protected FileMapper getFileMapper() {
		return fileMapper;
	}

	@Override
	protected FileSource fileSource() {
		return FileSource.LOCAL;
	}

	@Override
	protected boolean deleteFile(FileInfoDTO fileInfoDTO) {
		return FileUtil.deleteFile(fileInfoDTO.getPath());
	}

	@Override
	protected void uploadFile(MultipartFile file, FileInfoDTO fileInfoDTO) throws Exception {
		int index = fileInfoDTO.getName().lastIndexOf(".");
		// 文件扩展名
		String fileSuffix = fileInfoDTO.getName().substring(index);

		String suffix = "/" + LocalDate.now().toString().replace("-", "/") + "/" + fileInfoDTO.getId() + fileSuffix;

		String path = localFilePath + suffix;
		String url = urlPrefix + suffix;
		fileInfoDTO.setPath(path);
		fileInfoDTO.setUrl(url);

		FileUtil.save(file, path);
	}

}
