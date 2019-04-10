package com.cloud.mail;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.alibaba.dubbo.spring.boot.annotation.EnableDubboConfiguration;
import com.ctrip.framework.apollo.spring.annotation.EnableApolloConfig;

/**
 * 邮件中心
 * 使用dubbo
 * @author zhouyu
 *
 */
@EnableDubboConfiguration
@EnableApolloConfig
@SpringBootApplication
public class MailApplication 
{
    public static void main( String[] args )
    {
        SpringApplication.run(MailApplication.class, args);
    }
}
