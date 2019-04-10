package com.cloud.file.controller;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.cloud.common.utils.PageUtil;
import com.cloud.file.config.FileServiceFactory;
import com.cloud.file.mapper.FileMapper;
import com.cloud.file.service.FileService;
import com.cloud.model.common.Page;
import com.cloud.model.file.FileInfoDTO;
import com.cloud.model.log.LogAnnotation;
import com.netflix.infix.lang.infix.antlr.EventFilterParser.filter_return;

@RestController
@RequestMapping("/files")
public class FileController {
	
	@Autowired
	private FileServiceFactory fileServiceFactory;
	
	@Autowired
	private FileMapper fileMapper;
	
	
	/**
	 * 文件上传
	 * @param file
	 * @param fileSource
	 * @return
	 * @throws Exception
	 */
	@LogAnnotation(module = "文件上传", recordParam = false)
	@PostMapping
	public FileInfoDTO upload(@RequestParam("file") MultipartFile file, String fileSource) throws Exception {
		FileService fileService = fileServiceFactory.getFileService(fileSource);
		return fileService.upload(file);
	}
	
	/**
	 * layui富文本文件自定义上传
	 * 
	 * @param file
	 * @param fileSource
	 * @return
	 * @throws Exception
	 */
	@LogAnnotation(module = "文件上传", recordParam = false)
	@PostMapping("/layui")
	public Map<String, Object> uploadLayui(@RequestParam("file") MultipartFile file, String fileSource)
			throws Exception {
		FileInfoDTO fileInfo = upload(file, fileSource);

		Map<String, Object> map = new HashMap<>();
		map.put("code", 0);
		Map<String, Object> data = new HashMap<>();
		data.put("src", fileInfo.getUrl());
		map.put("data", data);

		return map;
	}
	
	/**
	 * 文件删除
	 * 
	 * @param id
	 */
	@LogAnnotation(module = "文件删除")
	@PreAuthorize("hasAuthority('file:del')")
	@DeleteMapping("/{id}")
	public void delete(@PathVariable String id) {
		FileInfoDTO fileInfoDTO = fileMapper.getById(id);
		if (fileInfoDTO != null) {
			FileService fileService = fileServiceFactory.getFileService(fileInfoDTO.getSource());
			fileService.delete(fileInfoDTO);
		}
	}
	
	
	/**
	 * 文件查询
	 * 
	 * @param params
	 * @return
	 */
	@PreAuthorize("hasAuthority('file:query')")
	@GetMapping
	public Page<FileInfoDTO> findFiles(@RequestParam Map<String, Object> params) {
		int total = fileMapper.count(params);
		List<FileInfoDTO> list = Collections.emptyList();
		if (total > 0) {
			PageUtil.pageParamConver(params, true);

			list = fileMapper.findData(params);
		}
		return new Page<>(total, list);
	}

}
