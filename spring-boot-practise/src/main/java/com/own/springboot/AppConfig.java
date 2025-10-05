package com.own.springboot;

import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;

//@Configuration

//public class AppConfig {
//
//	@Bean
//	public Sim sim() {
//		return new Jio(); // Change to new Airtel() to switch implementations
//	}
//}

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = "com.own")
public class AppConfig {

//	@Bean
//	public Sim sim() {
//		return new Jio(); // Change to new Airtel() to switch implementations
//	}
}
