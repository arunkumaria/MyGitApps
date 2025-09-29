package com.own.microservice_openfeign_emp_service.config;

import com.own.microservice_openfeign_emp_service.*;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EmployeeConfig {

	@Bean
	public ModelMapper modelMapperBean() {
		return new ModelMapper();
	}
}
