package com.ashaxm.springboot.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//开启组件扫描和自动装配
@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		//负责启动引导应用程序
		SpringApplication.run(Application.class, args);
	}
}
