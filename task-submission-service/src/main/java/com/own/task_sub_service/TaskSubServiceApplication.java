package com.own.task_sub_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.own.mainapp", "com.own.task_sub_service"})
@EnableFeignClients
public class TaskSubServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(TaskSubServiceApplication.class, args);
	}

}
