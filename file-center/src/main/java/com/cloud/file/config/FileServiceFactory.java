package com.cloud.file.config;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cloud.file.service.FileService;
import com.cloud.model.file.FileSource;

/**
 * FileService工厂类
 * @author zhouyu
 *
 */
@Component
public class FileServiceFactory {
	
	private Map<FileSource, FileService> map = new HashMap<>();
	
	@Autowired
	private FileService localFileService;
//	
//	@Autowired
//	private FileService aliyunFileService;
	
	
	// 构建完成后自动初始化
	public @PostConstruct void init() {
		map.put(FileSource.LOCAL, localFileService);
//		map.put(FileSource.ALIYUN, aliyunFileService);
	}
	
	public FileService getFileService(String fileSource) {
		if (StringUtils.isBlank(fileSource)) {// 默认用本地存储
			return localFileService;
		}

		FileService fileService = map.get(FileSource.valueOf(fileSource));
		if (fileService == null) {
			throw new IllegalArgumentException("请检查FileServiceFactory类的init方法，看是否有" + fileSource + "对应的实现类");
		}

		return fileService;
	}
	
}
