package com.task.dynamic;

import com.task.dynamic.config.systeminit.StartupInitialize;
import com.task.dynamic.spring.SpringContextUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
@Slf4j
public class DynamicApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext run = SpringApplication.run(DynamicApplication.class, args);
		// 注入上下文
		SpringContextUtil.setApplicationContext(run);
		// 初始化项目任务
		StartupInitialize startupInitialize = SpringContextUtil.getBean("startupInitialize", StartupInitialize.class);
		if (startupInitialize == null) {
			log.error("项目启动失败，初始化任务未完成");
		} else {
			startupInitialize.init(run);
			log.info("初始化任务执行完成");
		}
	}

}
