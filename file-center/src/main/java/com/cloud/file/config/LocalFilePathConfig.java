package com.cloud.file.config;

import java.io.File;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ResourceUtils;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 配置本地文件路径，类似于在tomcat下的upload
 * @author zhouyu
 *
 */
@Configuration
public class LocalFilePathConfig {
	
	/**
	 * 上传文件存储在本地的根路径
	 */
	@Value("${file.local.path}")
	private String localFilePath;
	
	/**
	 * url前缀，便于资源服务器放行
	 */
	@Value("${file.local.prefix}")
	private String localFilePrefix;
	
	@Bean
	public WebMvcConfigurer webMvcConfigurerAdapter() {
		return new WebMvcConfigurer() {

			/**
			 * 外部文件访问<br>
			 */
			@Override
			public void addResourceHandlers(ResourceHandlerRegistry registry) {
				registry.addResourceHandler(localFilePrefix + "/**")
						.addResourceLocations(ResourceUtils.FILE_URL_PREFIX + localFilePath + File.separator);
			}

		};
	}

}
