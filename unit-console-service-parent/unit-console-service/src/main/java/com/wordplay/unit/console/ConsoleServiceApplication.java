package com.wordplay.unit.console;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author zhuangpf
 */
@EnableDiscoveryClient
@EnableFeignClients(basePackages = {"com.wordplay.unit.console.client.api"})
@MapperScan(basePackages = {
		"com.wordplay.unit.starter.data.mp.mapper", // 基础字段
		"com.wordplay.unit.starter.sysparam.mapper",
		"com.wordplay.unit.starter.i18n.mapper",
		"com.wordplay.unit.starter.mail.mapper",
		"com.wordplay.unit.starter.sms.mapper",
		"com.wordplay.unit.starter.file.mapper",
		"com.wordplay.unit.starter.mq.mapper",
		"com.wordplay.unit.starter.rbac.mapper",
})
@SpringBootApplication
@EnableTransactionManagement
@ComponentScan(basePackages = {"com.wordplay"})
public class ConsoleServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ConsoleServiceApplication.class, args);
	}

}

