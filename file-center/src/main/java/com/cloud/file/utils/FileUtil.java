package com.cloud.file.utils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.web.multipart.MultipartFile;

import com.cloud.model.file.FileInfoDTO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FileUtil {
	
	public static FileInfoDTO getFileInfo(MultipartFile file) throws IOException {
		
		String md5 = fileMd5(file.getInputStream());
		
		FileInfoDTO fileInfoDTO = new FileInfoDTO();
		fileInfoDTO.setId(md5);
		fileInfoDTO.setName(file.getOriginalFilename());
		fileInfoDTO.setContentType(file.getContentType());
		fileInfoDTO.setIsImg(fileInfoDTO.getContentType().startsWith("image/") ? 1 : 0);
		fileInfoDTO.setSize(file.getSize());
		fileInfoDTO.setCreateTime(new Date());
		
		return fileInfoDTO;
	}
	
	/**
	 * 文件的md5
	 * 
	 * @param inputStream
	 * @return
	 */
	public static String fileMd5(InputStream inputStream) {
		try {
			return DigestUtils.md5Hex(inputStream);
		} catch (IOException e) {
			log.error("获取文件md5异常！", e);
		}
		return null;
	}
	
	
	/**
	 * 将文件保存在本地
	 * @param file
	 * @param path
	 * @return
	 */
	public static String save(MultipartFile file, String path) {
		try {
			File targetFile = new File(path);
			if(targetFile.exists()) {
				return path;
			}
			if (!targetFile.getParentFile().exists()) {
				targetFile.getParentFile().mkdirs();
			}
			file.transferTo(targetFile);
			return path;
		} catch (Exception e) {
			log.error("保存文件异常！", e);
		}
		return null;
	}
	
	/**
	 * 删除本地文件
	 *
	 * @param pathname
	 * @return
	 */
	public static boolean deleteFile(String pathname) {
		File file = new File(pathname);
		if (file.exists()) {
			boolean flag = file.delete();

			if (flag) {
				File[] files = file.getParentFile().listFiles();
				if (files == null || files.length == 0) {
					file.getParentFile().delete();
				}
			}

			return flag;
		}

		return false;
	}

}
