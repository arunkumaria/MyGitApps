package com.own;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class EcomNotificationServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(EcomNotificationServiceApplication.class, args);
	}

}
